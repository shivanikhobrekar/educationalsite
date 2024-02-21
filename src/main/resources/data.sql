INSERT INTO course (name) VALUES ('Mathematics');
INSERT INTO course (name) VALUES ('Physics');
INSERT INTO course (name) VALUES ('Chemistry');

INSERT INTO student (full_name_english, full_name_arabic, email, telephone_number, address)
VALUES ('John Smith', 'جون سميث', 'john@example.com', '123456789', '123 Main St, City');

INSERT INTO student (full_name_english, full_name_arabic, email, telephone_number, address)
VALUES ('Jane Doe', 'جين دو', 'jane@example.com', '987654321', '456 Elm St, Town');

INSERT INTO student_course (student_id, course_id) VALUES (1, 1); -- John Smith enrolled in Mathematics
INSERT INTO student_course (student_id, course_id) VALUES (1, 2); -- John Smith enrolled in Physics
INSERT INTO student_course (student_id, course_id) VALUES (2, 2); -- Jane Doe enrolled in Physics
INSERT INTO student_course (student_id, course_id) VALUES (2, 3); -- Jane Doe enrolled in Chemistry
