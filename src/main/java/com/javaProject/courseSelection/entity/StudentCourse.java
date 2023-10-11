package com.javaProject.courseSelection.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "course_schedule")
public class StudentCourse {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "course_date")
    private LocalDate courseDate;

    @Column(name = "course_syllabus")
    private String courseSyllabus;

    @Column(name = "course_program")
    private String courseProgram;

    @Column(name = "course_overview")
    private String courseOverview;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public LocalDate getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(LocalDate courseDate) {
        this.courseDate = courseDate;
    }

    public String getCourseSyllabus() {
        return courseSyllabus;
    }

    public void setCourseSyllabus(String courseSyllabus) {
        this.courseSyllabus = courseSyllabus;
    }

    public String getCourseProgram() {
        return courseProgram;
    }

    public void setCourseProgram(String courseProgram) {
        this.courseProgram = courseProgram;
    }

    public String getCourseOverview() {
        return courseOverview;
    }

    public void setCourseOverview(String courseOverview) {
        this.courseOverview = courseOverview;
    }

    public StudentCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

    public StudentCourse(int id, String courseCode, LocalDate courseDate, String courseSyllabus, String courseProgram, String courseOverview) {
        super();
        this.id = id;
        this.courseCode = courseCode;
        this.courseDate = courseDate;
        this.courseSyllabus = courseSyllabus;
        this.courseProgram = courseProgram;
        this.courseOverview = courseOverview;
    }
    
}
