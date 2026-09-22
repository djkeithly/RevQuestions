-- Using the chinook database connection

-- Get all customers who have a lifetime spending of over 40
SELECT c.customer_id, c.first_name, c.last_name, SUM(i.total) AS total_spent
    FROM customer c
    JOIN invoice i
        ON c.customer_id = i.customer_id
        GROUP BY c.customer_id
        HAVING SUM(i.total) > 40        
        ORDER BY total_spent DESC;

-- Select te top five most popular genres and their sales
SELECT g.name, SUM(i.quantity)
    FROM genre g
    join track t
        ON t.genre_id = g.genre_id
    join invoice_line i 
        ON i.track_id = t.track_id
    GROUP BY g.genre_id
    ORDER BY SUM(i.quantity) DESC
    LIMIT 5;

-- List how many different genres a customer has bought from
SELECT c.customer_id, c.first_name, c.last_name, COUNT(DISTINCT t.genre_id) as genre_count
    FROM customer c
    JOIN invoice i
        ON c.customer_id = i.customer_id
    JOIN invoice_line l
        ON i.invoice_id = l.invoice_id
    JOIN track t
        ON l.track_id = t.track_id
    GROUP BY c.customer_id, c.first_name, c.last_name
        HAVING COUNT(DISTINCT t.genre_id) >= 5
    ORDER BY genre_count DESC;

-- Get how many customers an employee supports and how much those customers pay
SELECT e.employee_id, e.first_name, e.last_name, COUNT(DISTINCT c.customer_id) as c_supported, COALESCE(SUM(i.total),0) as c_spend
    FROM employee e
    LEFT JOIN customer c
        ON c.support_rep_id = e.employee_id
    LEFT JOIN invoice i
        ON c.customer_id = i.customer_id
    GROUP BY e.employee_id, e.first_name, e.last_name
    ORDER BY c_spend DESC;

-- Get customers who spend more than the average customer
WITH customer_spending AS (
    SELECT
        c.customer_id,
        SUM(i.total) AS total_spending
    FROM customer c
    JOIN invoice i
        ON c.customer_id = i.customer_id
    GROUP BY c.customer_id
)
SELECT c.customer_id, c.first_name, c.last_name, SUM(i.total) as total_spending
    FROM customer c
    JOIN invoice i
        ON i.customer_id = c.customer_id
    GROUP BY c.customer_id, c.first_name, c.last_name
        HAVING SUM(i.total) >= (
            SELECT AVG(total_spending)
                FROM customer_spending
        )
    ORDER BY total_spending DESC;

-- Get how many tracks each album sold
SELECT a.name, SUM(il.quantity * il.unit_price)
    FROM artist a
    JOIN album al
        ON a.artist_id = al.artist_id
    JOIN track t
        ON t.album_id = al.album_id
    join invoice_line il
        ON il.track_id = t.track_id
    GROUP BY a.artist_id
    ORDER BY SUM(il.quantity * il.unit_price) DESC
        LIMIT 1;

-- Practice with rank, get a customer's favorite genre
WITH ranked_genres AS (
    SELECT c.customer_id, c.first_name, c.last_name, g.name, SUM(il.quantity) AS total_purchased, RANK() OVER(
        PARTITION BY c.customer_id
        ORDER By SUM(il.quantity) DESC
    ) AS genre_rank
        FROM customer C
        JOIN invoice I 
            ON i.customer_id = C.customer_id
        JOIN invoice_line il
            ON il.invoice_id = I.invoice_id
        JOIN track t
            ON t.track_id = il.track_id
        join genre g
            ON g.genre_id = t.genre_id
        GROUP BY c.customer_id, g.name 
)
SELECT c.customer_id, c.first_name, c.last_name, r.name, r.total_purchased
    FROM customer c
    JOIN ranked_genres r
        ON c.customer_id = r.customer_id
    WHERE r.genre_rank = 1;

-- List all the albums that have had zero sales
WITH sale AS(
    SELECT t.track_id, t.album_id
    FROM track t
    LEFT JOIN invoice_line il
        ON il.track_id = t.track_id
    WHERE il.invoice_line_id IS NOT NULL
)
SELECT a.album_id, a.title, ar.name
    FROM album a
        JOIN artist ar
            ON a.artist_id = ar.artist_id
        WHERE NOT EXISTS(
            SELECT 1
                FROM sale s
                WHERE a.album_id = s.album_id
        );

-- Make a function that looks up customer spending given a customer id
CREATE OR REPLACE FUNCTION get_customer_spending(c_id INT)
RETURNS TABLE(total NUMERIC) AS $$
BEGIN
    RETURN QUERY
        SELECT SUM(i.total)
            FROM invoice i
            WHERE i.customer_id = c_id
            GROUP BY(i.customer_id);
END;
$$ language plpgsql;

SELECT * FROM get_customer_spending(1);

DROP FUNCTION get_customer_spending(integer);

-- Get the customer who spent the most in each country
SELECT c.customer_id, c.first_name, c.last_name, c.country, ranked.totalMoney
    FROM customer c
    JOIN(
        SELECT c.customer_id, SUM(i.total) AS totalMoney, RANK() OVER(
            PARTITION BY c.country
            ORDER BY SUM(i.total) DESC
        ) AS rank
            FROM customer c
            JOIN invoice i
                ON c.customer_id = i.customer_id
            GROUP BY c.customer_id) AS ranked
        ON c.customer_id = ranked.customer_id
        WHERE ranked.rank = 1;

-- Create a view
DROP VIEW IF EXISTS customer_spending

CREATE VIEW customer_spending AS
    SELECT c.customer_id, c.first_name, c.last_name, c.country, SUM(i.total) AS total_spent
        FROM customer c
        JOIN invoice i
            ON c.customer_id = i.customer_id
        GROUP BY c.customer_id;

SELECT *
    FROM customer_spending
    WHERE total_spent > 45
    ORDER BY total_spent DESC;

-- Create a function

CREATE OR REPLACE FUNCTION get_customer_total(c_id INT)
RETURNS TABLE(total NUMERIC) AS $$
    BEGIN
        RETURN QUERY
            SELECT SUM(i.total)
                FROM invoice i
                WHERE i.customer_id = c_id
                GROUP BY i.customer_id;
    END;
$$ language plpgsql;

SELECT * FROM get_customer_total(6);

DROP FUNCTION get_customer_total(integer);