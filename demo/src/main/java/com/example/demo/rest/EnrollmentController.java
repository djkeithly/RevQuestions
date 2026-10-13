package com.example.demo.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import com.example.demo.domain.Enrollment;
import com.example.demo.dto.EnrollmentWriteDto;
import com.example.demo.service.EnrollmentService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController 
@RequestMapping("/api/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @PostMapping()
    public ResponseEntity<Enrollment> insertEnrollment(@Valid @RequestBody EnrollmentWriteDto enrollment) {
        Enrollment savedEnrollment = enrollmentService.insertEnrollment(enrollment);
        
        return ResponseEntity.created(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedEnrollment.getId())
                    .toUri()    
            )
            .body(savedEnrollment);
    }
    
    
}
