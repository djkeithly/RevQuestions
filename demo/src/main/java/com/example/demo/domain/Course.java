package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity 
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 20)
    @NotBlank
    @Column(name = "name", length = 20)
    private String name;

    @OneToMany(mappedBy = "course", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore 
    private List<Enrollment> enrollments = new ArrayList<>();

    public Course() {
    }

    public Course(@Size(max = 20) @NotBlank String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Enrollment> getEnrollment() {
        return enrollments;
    }

    public void addEnrollment(Enrollment enrollment){
        this.enrollments.add(enrollment);
        enrollment.setCourse(this);
    }

    public void removeEnrollment(Enrollment enrollment){
        this.enrollments.remove(enrollment);
        enrollment.setCourse(null);
    }
}
