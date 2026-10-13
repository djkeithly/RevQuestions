package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.School;

public interface SchoolRepository extends JpaRepository<School, Integer> {

}
