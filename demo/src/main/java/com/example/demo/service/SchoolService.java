package com.example.demo.service;

import java.util.List;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.School;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.dao.SchoolRepository;

@Service 
public class SchoolService {
    
    private final SchoolRepository schoolRepo;
    
    // TO-DO Implement logging
    //private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepo = schoolRepository;
    }

    // SCHOOLS
    @Transactional(readOnly = true)
    public School getSchoolById(int id) {
          School school = schoolRepo.findById(id)
            .orElseThrow(() -> 
                new RecordNotFoundException(
                    "School not found with id: " + id
                )
            );


        return school;
    }


    @Transactional(readOnly = true)
    public List<School> getAllSchools() {
        return schoolRepo.findAll();
    }


    @Transactional
    public School insertSchool(School school) {
        return schoolRepo.save(school);
    }
}