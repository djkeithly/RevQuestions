-- Get all fields and records from customer
SELECT * FROM CUSTOMER;

-- Get all fields from customer, but only if they are from Arizona
-- 1 Result with state = AZ which is what I assume is meant
SELECT * FROM customer
    WHERE state = 'AZ';

-- Get all invoices older than 6 months 
-- Should be no results. Results show up if we do about 1 year
SELECT * FROM INVOICE
    WHERE invoice_date BETWEEN NOW() - INTERVAL'6 months' AND NOW();

-- Update all customer phone numbers to NULL if they don’t follow this format: ‘+1 555 555-5555
-- Changing to allowing () because without it, all the numbers switch to null
UPDATE CUSTOMER
    SET phone = NULL
    WHERE phone !~ '^\+1 ([0-9]{3}|([0-9]{3})) [0-9]{3}-[0-9]{4}$';

-- Get all tracks that are longer than 180000 milliseconds 
SELECT * FROM track
    WHERE milliseconds > 180000;

-- Update all customers not in the USA so that their country=USA and address, city, & state are NULL
UPDATE customer
    SET country = 'USA',
    address = null,
    city = null,
    state = null
    WHERE country != 'USA';

-- Given a customer_id, return their total spending across all invoices using a function 
CREATE FUNCTION getSpending(user_ID INTEGER)
RETURNS INTEGER AS $$
BEGIN
    RETURN (
        SELECT SUM(total)
        FROM invoice
        WHERE user_ID = customer_id
    );
END;
$$ LANGUAGE plpgsql;

SELECT getSpending(10);

/*
Given an employee_id + new_manager_id, create a stored procedure to update an Employee’s ReportsTo field.
Prevent an employee reporting to themselves, reporting to a non-existence employee, or creating a circular management relationship
*/
CREATE PROCEDURE add_employee_reporting_to(employed_ID INTEGER, manager_ID INTEGER)
LANGUAGE plpgsql
AS $$
DECLARE 
    current_manager INTEGER;
BEGIN
    
    current_manager := manager_ID;

    WHILE current_manager IS NOT NULL LOOP

        IF current_manager = employed_ID THEN
            RAISE EXCEPTION 'Circular Management ERROR';
        END IF;

        SELECT reports_to
            INTO current_manager
            FROM employee
            WHERE employee_id = current_manager;

    END LOOP;

    UPDATE employee
        SET reports_to = manager_ID
        WHERE employee_id = employed_id;
END;
$$;

CALL add_employee_reporting_to(7, 8);

SELECT * FROM EMPLOYEE;