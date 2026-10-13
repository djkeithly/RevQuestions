package com.example.demo.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.domain.Course;
import com.example.demo.domain.Enrollment;
import com.example.demo.domain.Student;
import com.example.demo.dto.EnrollmentWriteDto;
import com.example.demo.dto.StudentWriteDto;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;

import com.example.demo.dao.CourseRepository;
import com.example.demo.dao.EnrollmentRepository;
import com.example.demo.dao.StudentRepository;

@Service 
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepo;
    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;

    public EnrollmentService(EnrollmentRepository enrollmentRepo, StudentRepository studentRepo, CourseRepository courseRepo) {
        this.enrollmentRepo = enrollmentRepo;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    @Query("SELECT e FROM Enrollment e")
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepo.findAll();
    }
    
    @Transactional
    public Enrollment insertEnrollment(EnrollmentWriteDto enrollment){
        Student student = studentRepo.findById(enrollment.getStudentId()).orElse(null);
        Course course = courseRepo.findById(enrollment.getCourseId()).orElse(null);

        Enrollment newEnrollment = new Enrollment(LocalDate.now(), null, student, course);

        student.addEnrollment(newEnrollment);
        course.addEnrollment(newEnrollment);

        return enrollmentRepo.save(newEnrollment);
    }
}
