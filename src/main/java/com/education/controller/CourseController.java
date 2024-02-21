package com.education.controller;

import com.education.model.Course;
import com.education.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    private HttpHeaders createHeaders(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        return headers;
    }

    private <T> ResponseEntity<T> sendRequest1(String jwtToken, String apiUrl, HttpMethod method, Object body, Class<T> responseType) {
        HttpHeaders headers = createHeaders(jwtToken);
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(apiUrl, method, requestEntity, responseType);
    }

    private <T> ResponseEntity<T> sendRequest(String jwtToken, String apiUrl, HttpMethod method, Object body, ParameterizedTypeReference<T> responseType) {
        HttpHeaders headers = createHeaders(jwtToken);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(apiUrl, method, requestEntity, responseType);
    }


    @PostMapping("/")
    public ResponseEntity<Course> addCourse(@RequestHeader("Authorization") String jwtToken, @RequestBody Course course) {
        String apiUrl = "";
        ResponseEntity<Course> response = sendRequest1(jwtToken, apiUrl, HttpMethod.POST, course, Course.class);
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/")
    public ResponseEntity<List<Course>> getCourses(@RequestHeader("Authorization") String jwtToken) {
        String apiUrl = "";
        ParameterizedTypeReference<List<Course>> typeReference = new ParameterizedTypeReference<List<Course>>() {};
        ResponseEntity<List<Course>> response = sendRequest(jwtToken, apiUrl, HttpMethod.GET, null, typeReference);
        return ResponseEntity.ok(response.getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@RequestHeader("Authorization") String jwtToken, @PathVariable Long id, @RequestBody Course courseDetails) {
        String apiUrl = "";
        ResponseEntity<Course> response = sendRequest1(jwtToken, apiUrl + "/" + id, HttpMethod.PUT, courseDetails, Course.class);
        return ResponseEntity.ok(response.getBody());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@RequestHeader("Authorization") String jwtToken, @PathVariable Long id) {
        String apiUrl = "";
        ResponseEntity<Void> response = sendRequest1(jwtToken, apiUrl + "/" + id, HttpMethod.DELETE, null, Void.class);
        return ResponseEntity.noContent().build();
    }
}
