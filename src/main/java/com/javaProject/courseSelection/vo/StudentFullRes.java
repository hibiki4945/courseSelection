package com.javaProject.courseSelection.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentFullRes {

    private String code;
    
    private String message;
    
    private String studentId;
    
    private String name;
    
//    private int authorizationRank;
    
    private String email;

    private LocalDate birthday;

    private LocalDateTime registrationTime;
    
    private boolean activation;
    
    private boolean subsidy;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public boolean isActivation() {
        return activation;
    }

    public void setActivation(boolean activation) {
        this.activation = activation;
    }

    public boolean isSubsidy() {
        return subsidy;
    }

    public void setSubsidy(boolean subsidy) {
        this.subsidy = subsidy;
    }

    public StudentFullRes() {
        super();
        // TODO Auto-generated constructor stub
    }

    public StudentFullRes(String code, String message, String studentId, String name, String email, LocalDate birthday, LocalDateTime registrationTime, boolean activation,
            boolean subsidy) {
        super();
        this.code = code;
        this.message = message;
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.registrationTime = registrationTime;
        this.activation = activation;
        this.subsidy = subsidy;
    }

}
