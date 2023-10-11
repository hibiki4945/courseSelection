package com.javaProject.courseSelection.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column(name = "course_code")
    private String courseCode;
    
    @Column(name = "name")
    private String name;

    @Column(name = "teacher")
    private String teacher;
    
    @Column(name = "explanation")
    private String explanation;
    
    @Column(name = "class_time_table")
    private Map<String, Boolean> classTimeTable;

    @Column(name = "course_start_date")
    private LocalDate courseStartDate;

    @Column(name = "course_end_date")
    private LocalDate courseEndDate;

    @Column(name = "on_shelf")
    private boolean onShelf;

    @Column(name = "course_position")
    private String coursePosition;
    
    @Column(name = "build_time")
    private LocalDateTime buildTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "update_employee")
    private String updateEmployee;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Map<String, Boolean> getClassTimeTable() {
        return classTimeTable;
    }

    public void setClassTimeTable(Map<String, Boolean> classTimeTable) {
        this.classTimeTable = classTimeTable;
    }

    public LocalDate getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(LocalDate courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public LocalDate getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(LocalDate courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public boolean isOnShelf() {
        return onShelf;
    }

    public void setOnShelf(boolean onShelf) {
        this.onShelf = onShelf;
    }

    public String getCoursePosition() {
        return coursePosition;
    }

    public void setCoursePosition(String coursePosition) {
        this.coursePosition = coursePosition;
    }

    public LocalDateTime getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(LocalDateTime buildTime) {
        this.buildTime = buildTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateEmployee() {
        return updateEmployee;
    }

    public void setUpdateEmployee(String updateEmployee) {
        this.updateEmployee = updateEmployee;
    }

    public Course() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Course(String courseCode, String name, String teacher, String explanation, Map<String, Boolean> classTimeTable, LocalDate courseStartDate, LocalDate courseEndDate,
            boolean onShelf, String coursePosition, LocalDateTime buildTime, LocalDateTime updateTime, String updateEmployee) {
        super();
        this.courseCode = courseCode;
        this.name = name;
        this.teacher = teacher;
        this.explanation = explanation;
        this.classTimeTable = classTimeTable;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.onShelf = onShelf;
        this.coursePosition = coursePosition;
        this.buildTime = buildTime;
        this.updateTime = updateTime;
        this.updateEmployee = updateEmployee;
    }
    
}
