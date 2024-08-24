SELECT * FROM student s
WHERE s.age
BETWEEN 10 and 20

SELECT s."name" FROM student s

SELECT * FROM student s
WHERE s."name" LIKE '%o%'

SELECT * FROM student s
WHERE s.age < s.id

SELECT * FROM student s
ORDER BY s.age

