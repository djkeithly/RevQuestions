USE SCHEMA student_tracker;
Set search_path to public;
SHOW search_path;


SELECT *
FROM public.student
LIMIT 1000;

DROP TABLE student Cascade;
DROP TABLE course Cascade;
DROP TABLE school Cascade;
DROP TABLE enrollment Cascade;

SELECT * FROM student;
SELECT * FROM school;
SELECT * FROM course;
SELECT * FROM enrollment;