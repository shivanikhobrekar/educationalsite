package com.education.service;

import com.education.model.Course;
import com.education.model.Student;
import com.education.repository.CourseRepository;
import com.education.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Student registerStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public void enrollStudentToCourse(Long studentId, Long courseId) throws ChangeSetPersister.NotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        student.getCourses().add(course);
        studentRepository.save(student);
    }

    public List<Student> getStudentsByCourse(Long courseId) throws ChangeSetPersister.NotFoundException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        return course.getStudents();
    }

    public void updateStudentCourses(Long studentId, List<Long> courseIds) throws ChangeSetPersister.NotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        List<Course> courses = courseRepository.findAllById(courseIds);
        student.setCourses(courses);
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) throws ChangeSetPersister.NotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        studentRepository.delete(student);
    }
}
