package com.javaProject.courseSelection.vo;

import java.time.LocalDate;

public class StudentCreateReq {

    private String studentId;
    
    private String name;
    
//    private int authorizationRank;
    
    private String email;
    
    private LocalDate birthday;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public StudentCreateReq() {
        super();
        // TODO Auto-generated constructor stub
    }

    public StudentCreateReq(String studentId, String name, String email, LocalDate birthday) {
        super();
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }

}
