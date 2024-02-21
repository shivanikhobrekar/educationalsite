-- Create the Course table
CREATE TABLE IF NOT EXISTS course (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL
    );

-- Create the Student table
CREATE TABLE IF NOT EXISTS student (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       full_name_english VARCHAR(255) NOT NULL,
    full_name_arabic VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    telephone_number VARCHAR(20),
    address VARCHAR(255)
    );

-- Create the Student-Course mapping table
CREATE TABLE IF NOT EXISTS student_course (
                                              id INT AUTO_INCREMENT PRIMARY KEY,
                                              student_id INT NOT NULL,
                                              course_id INT NOT NULL,
                                              FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
    );
