package com.example.demo.dao;

import java.util.List;

import com.example.demo.domain.Student;

public interface StudentDAO {

    Student insert(Student student);
    List<Student> findAll();
    Student findById(int id);
    void delete(Student student);
    List<Student> findByLastName(String lastName);
}