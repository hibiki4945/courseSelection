package com.javaProject.courseSelection.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Map;

@Entity
@Table(name = "student_number")
public class CourseSchedule {

    @Id
    @Column(name = "student_number")
    private String studentNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "selected_course")
    private Map<String, Boolean> selected_course;

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Boolean> getSelected_course() {
        return selected_course;
    }

    public void setSelected_course(Map<String, Boolean> selected_course) {
        this.selected_course = selected_course;
    }

    public CourseSchedule() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CourseSchedule(String studentNumber, String name, Map<String, Boolean> selected_course) {
        super();
        this.studentNumber = studentNumber;
        this.name = name;
        this.selected_course = selected_course;
    }
    
}
