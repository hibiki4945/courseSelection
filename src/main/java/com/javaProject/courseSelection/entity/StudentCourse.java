package com.javaProject.courseSelection.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.awt.TextArea;

@Entity
@Table(name = "student_course")
public class StudentCourse {

    @Id
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "name")
    private String name;

//    @Column(name = "selected_course")
//    private Map<String, Boolean> selected_course;
    
    @Column(name = "selected_course")
    private TextArea selected_course;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TextArea getSelected_course() {
        return selected_course;
    }

    public void setSelected_course(TextArea selected_course) {
        this.selected_course = selected_course;
    }

    public StudentCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

    public StudentCourse(String studentId, String name, TextArea selected_course) {
        super();
        this.studentId = studentId;
        this.name = name;
        this.selected_course = selected_course;
    }

}
