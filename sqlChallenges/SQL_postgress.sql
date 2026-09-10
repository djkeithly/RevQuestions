------------------------------------------------
-- 9/9/2026
------------------------------------------------

SET search_path TO public;

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

/* 
Create a new schema: pets
Create two related tables: Customer + Pets
Demonstrate populating records into these tables 
*/
DROP SCHEMA IF EXISTS PETS CASCADE;
CREATE SCHEMA PETS;

SET search_path TO pets;

CREATE TABLE customer(
    name        VARCHAR(20),
    id          INT     PRIMARY KEY
);

CREATE TABLE pet(
    name        VARCHAR(20),
    tag         INT     PRIMARY KEY,
    OWNER       INT     REFERENCES customer(id)
);

INSERT INTO customer(name, id)
values
    ('Holly', 1),
    ('Dennis', 2),
    ('Noelle', 3),
    ('James', 4),
    ('Man', 5);

INSERT INTO pet(name, tag, owner)
VALUES
    ('Loki', 1, 2),
    ('Bullfrog', 2, 2),
    ('Elleon', 3, 3),
    ('Snake', 4, 3),
    ('Pet', 5, 5);

------------------------------------------------
-- 9/10/2026
------------------------------------------------

-- Get all invoice ids with the customers first name, last name, and the invoice total
SET search_path TO public;

SELECT i.invoice_id, c.first_name, c.last_name, i.total
FROM CUSTOMER c
JOIN INVOICE i 
    ON i.customer_id = c.customer_id;

-- Print the invoice id, customer's first name, and invoice total. But only if the invoice is over $30.
-- No invoices match this constraint, but it does work

SELECT i.invoice_id, c.first_name, c.last_name, i.total
FROM CUSTOMER c
JOIN INVOICE i 
    ON i.customer_id = c.customer_id
    WHERE i.total > 30;

-- Get all the invoices for USA customers in the last 6 months. Use a CTE. 

WITH usa_customer AS (
    SELECT customer_id
    FROM customer
        WHERE country = 'USA'
)
SELECT *
FROM invoice
WHERE customer_id IN (SELECT customer_id from usa_customer);

/*
Create a new table called record_logs
Fields: log_id, record_id, field_changed, last_update, old_value, new_value
*/
-- I am assuming given the second question that this is to track the customer table as there is no 'customer record' table
DROP TABLE IF EXISTS record_logs CASCADE;

CREATE TABLE record_logs(
    log_id              INT GENERATED ALWAYS AS IDENTITY     PRIMARY KEY,
    record_id           INT                                 REFERENCES customer(customer_id),
    field_changed       VARCHAR(30),
    last_update         DATE,
    old_value           VARCHAR(80),
    new_value           VARCHAR(80)
);

-- Create a trigger that tracks changes to customer records and logs the changes in our new table

CREATE OR REPLACE FUNCTION log_customer_record_change
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    IF NEW.first_name IS DISTINCT FROM OLD.first_name THEN
        INSERT INTO record_logs(log_id, record_id, field_changed, last_update, old_value, new_value)
        VALUES (DEFAULT, NEW.customer_id, 'first_name', NOW(), OLD.first_name, NEW.first_name);
    END IF;

    IF NEW.last_name IS DISTINCT FROM OLD.last_name THEN
        INSERT INTO record_logs(log_id, record_id, field_changed, last_update, old_value, new_value)
        VALUES (DEFAULT, NEW.customer_id, 'last_name', NOW(), OLD.last_name, NEW.last_name);
    END IF;

    IF NEW.company IS DISTINCT FROM OLD.company THEN
        INSERT INTO record_logs(log_id, record_id, field_changed, last_update, old_value, new_value)
        VALUES (DEFAULT, NEW.customer_id, 'company', NOW(), OLD.company, NEW.company);
    END IF;

    IF NEW.address IS DISTINCT FROM OLD.address THEN
        INSERT INTO record_logs(log_id, record_id, field_changed, last_update, old_value, new_value)
        VALUES (DEFAULT, NEW.customer_id, 'address', NOW(), OLD.address, NEW.address);
    END IF;

    IF NEW.city IS DISTINCT FROM OLD.city THEN
        INSERT INTO record_logs(log_id, record_id, field_changed, last_update, old_value, new_value)
        VALUES (DEFAULT, NEW.customer_id, 'city', NOW(), OLD.city, NEW.city);
    END IF;

    IF NEW.state IS DISTINCT FROM OLD.state THEN
        INSERT INTO record_logs(log_id, record_id, field_changed, last_update, old_value, new_value)
        VALUES (DEFAULT, NEW.customer_id, 'state', NOW(), OLD.state, NEW.state);
    END IF;

    IF NEW.country IS DISTINCT FROM OLD.country THEN
        INSERT INTO record_logs(log_id, record_id, field_changed, last_update, old_value, new_value)
        VALUES (DEFAULT, NEW.customer_id, 'country', NOW(), OLD.country, NEW.country);
    END IF;

    IF NEW.postal_code IS DISTINCT FROM OLD.postal_code THEN
        INSERT INTO record_logs(log_id, record_id, field_changed, last_update, old_value, new_value)
        VALUES (DEFAULT, NEW.customer_id, 'postal_code', NOW(), OLD.postal_code, NEW.postal_code);
    END IF;

    IF NEW.phone IS DISTINCT FROM OLD.phone THEN
        INSERT INTO record_logs(log_id, record_id, field_changed, last_update, old_value, new_value)
        VALUES (DEFAULT, NEW.customer_id, 'phone', NOW(), OLD.phone, NEW.phone);
    END IF;

    IF NEW.fax IS DISTINCT FROM OLD.fax THEN
        INSERT INTO record_logs(log_id, record_id, field_changed, last_update, old_value, new_value)
        VALUES (DEFAULT, NEW.customer_id, 'fax', NOW(), OLD.fax, NEW.fax);
    END IF;

    IF NEW.email IS DISTINCT FROM OLD.email THEN
        INSERT INTO record_logs(log_id, record_id, field_changed, last_update, old_value, new_value)
        VALUES (DEFAULT, NEW.customer_id, 'email', NOW(), OLD.email, NEW.email);
    END IF;

    IF NEW.support_rep_id IS DISTINCT FROM OLD.support_rep_id THEN
        INSERT INTO record_logs(log_id, record_id, field_changed, last_update, old_value, new_value)
        VALUES (DEFAULT, NEW.customer_id, 'email', NOW(), OLD.support_rep_id::VARCHAR, NEW.support_rep_id::VARCHAR);
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER users_update
AFTER UPDATE On public.customer
FOR EACH ROW
EXECUTE FUNCTION log_customer_record_change();
