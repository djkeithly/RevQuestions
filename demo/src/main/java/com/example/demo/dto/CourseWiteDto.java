package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseWiteDto {
    @JsonProperty 
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
