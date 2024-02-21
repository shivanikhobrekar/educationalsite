package com.education.controller;

import com.education.model.Course;
import com.education.model.Student;
import com.education.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final String apiUrl = "http://localhost:8080/api/v1";


    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    private HttpHeaders createHeaders(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        return headers;
    }

    @PostMapping("/")
    public ResponseEntity<Student> registerStudent(@RequestHeader("Authorization") String jwtToken, @RequestBody Student student) {
        HttpHeaders headers = createHeaders(jwtToken);
        HttpEntity<Student> request = new HttpEntity<>(student, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Student> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, Student.class);

        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getCourses(@RequestHeader("Authorization") String jwtToken) {
        HttpHeaders headers = createHeaders(jwtToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Course>> response = restTemplate.exchange(apiUrl, HttpMethod.GET, request,  new ParameterizedTypeReference<List<Course>>() {});

        return ResponseEntity.ok(response.getBody());
    }


    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<Void> enrollStudentToCourse(@PathVariable Long studentId, @PathVariable Long courseId, @RequestHeader("Authorization") String jwtToken) throws ChangeSetPersister.NotFoundException {
        studentService.enrollStudentToCourse(studentId, courseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable Long courseId, @RequestHeader("Authorization") String jwtToken) throws ChangeSetPersister.NotFoundException {
        HttpHeaders headers = createHeaders(jwtToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Student>> response = restTemplate.exchange(apiUrl + "/courses/" + courseId + "/students", HttpMethod.GET, request, new ParameterizedTypeReference<List<Student>>() {});
        return ResponseEntity.ok(response.getBody());
    }

    @PutMapping("/{studentId}/update-courses")
    public ResponseEntity<Void> updateStudentCourses(@PathVariable Long studentId, @RequestBody List<Long> courseIds, @RequestHeader("Authorization") String jwtToken) throws ChangeSetPersister.NotFoundException {
        studentService.updateStudentCourses(studentId, courseIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId, @RequestHeader("Authorization") String jwtToken) throws ChangeSetPersister.NotFoundException {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }
}
