package com.education.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Getter
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullNameEnglish;
    private String fullNameArabic;
    private String email;
    private String phoneNumber;
    private String address;

    @ManyToMany
    private List<Course> courses;

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
