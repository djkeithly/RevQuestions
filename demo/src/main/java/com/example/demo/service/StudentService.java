package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.SchoolRepository;
import com.example.demo.dao.StudentRepository;
import com.example.demo.domain.Enrollment;
import com.example.demo.domain.School;
import com.example.demo.domain.Student;
import com.example.demo.dto.StudentWriteDto;

@Service 
public class StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepo;
    private final SchoolRepository schoolRepo;

    public StudentService(
        StudentRepository studentRepo,
        SchoolRepository schoolRepo
    ) {
        this.studentRepo = studentRepo;
        this.schoolRepo = schoolRepo;
    }

    @Transactional 
    public Student insertStudent(StudentWriteDto student){
        School school = schoolRepo.findById(student.getSchoolId())
            .orElse(null);

        Student newStudent = new Student(
            student.getFirstName(),
            student.getLastName(),
            student.getEmail(),
            school
        );

        return studentRepo.save(newStudent);
    }

    @Transactional(readOnly = true)
    public List<Student> getAllStudents(){
        return studentRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Student getStudentById(int id){
        return studentRepo.findById(id)
            .orElse(null);
    }

    @Transactional
    public void deleteStudent(int id){
        Student student = getStudentById(id);

        studentRepo.delete(student);
    }

    @Transactional(readOnly = true)
    public List<Student> findStudentsByLastName(String lastName){
        return studentRepo.findByLastName(lastName);
    }

    @Transactional
    public Student updateStudent(int id, Student student){
        Student existingStudent = getStudentById(id);

        existingStudent.setEmail(student.getEmail());
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());

        return existingStudent;
    }

    @Transactional 
    public List<Enrollment> getEnrollmentsById(int id){
        Student existingStudent = getStudentById(id);

        return existingStudent.getEnrollment();
    }
}
