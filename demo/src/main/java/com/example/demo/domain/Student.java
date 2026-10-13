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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity 
@Table(name="student")
public class Student {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private School school;

    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @Email
    @NotBlank
    @Column(name = "email", length = 45, nullable = false, unique = true)
    private String email;

    @OneToMany( mappedBy = "student", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore 
    private List<Enrollment> enrollments = new ArrayList<>();

    public Student(String firstName, String lastName, @Email @NotBlank String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Student(){}

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public School getSchool(){
        return this.school;
    }

    public void setSchool(School school){
        this.school = school;
    }

    public Student(String firstName, String lastName, @Email @NotBlank String email, School school) {
        this.school = school;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.school = school;
    }
    
    public void addEnrollment(Enrollment enrollment){
        enrollments.add(enrollment);
        enrollment.setStudent(this);
    }

    public void removeEnrollment(Enrollment enrollment){
        enrollments.remove(enrollment);
        enrollment.setStudent(null);
    }

    @JsonIgnore 
    public List<Enrollment> getEnrollment(){
        return enrollments;
    }
}
