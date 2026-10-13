package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dao.CourseRepository;
import com.example.demo.domain.Course;
import com.example.demo.dto.CourseWiteDto;

import jakarta.transaction.Transactional;

@Service
public class CourseService {
    private CourseRepository courseRepo;

    public CourseService(CourseRepository courseRepo){
        this.courseRepo = courseRepo;
    }

    @Transactional 
    public Course createCourse(CourseWiteDto course){
        Course newCourse = new Course(course.getName());

        return courseRepo.save(newCourse);
    }

    @Transactional 
    public List<Course> getAllCourses(){
        return courseRepo.findAll();
    }
}
