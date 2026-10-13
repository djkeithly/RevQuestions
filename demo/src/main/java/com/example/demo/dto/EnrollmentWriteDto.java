package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrollmentWriteDto {
    @JsonProperty("student_id")
    private int studentId;

    @JsonProperty("course_id")
    private int courseId;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
