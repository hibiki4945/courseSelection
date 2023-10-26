package com.javaProject.courseSelection.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentSearchReq {

    private String studentId;
    
    private String name;
    
    private String email;

    private LocalDate birthdayStart;
    
    private LocalDate birthdayEnd;

    private LocalDateTime registrationTimeStart;
    
    private LocalDateTime registrationTimeEnd;
    
    private int activation;
    
    private int subsidy;

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

    public LocalDate getBirthdayStart() {
        return birthdayStart;
    }

    public void setBirthdayStart(LocalDate birthdayStart) {
        this.birthdayStart = birthdayStart;
    }

    public LocalDate getBirthdayEnd() {
        return birthdayEnd;
    }

    public void setBirthdayEnd(LocalDate birthdayEnd) {
        this.birthdayEnd = birthdayEnd;
    }

    public LocalDateTime getRegistrationTimeStart() {
        return registrationTimeStart;
    }

    public void setRegistrationTimeStart(LocalDateTime registrationTimeStart) {
        this.registrationTimeStart = registrationTimeStart;
    }

    public LocalDateTime getRegistrationTimeEnd() {
        return registrationTimeEnd;
    }

    public void setRegistrationTimeEnd(LocalDateTime registrationTimeEnd) {
        this.registrationTimeEnd = registrationTimeEnd;
    }

    public int getActivation() {
        return activation;
    }

    public void setActivation(int activation) {
        this.activation = activation;
    }

    public int getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(int subsidy) {
        this.subsidy = subsidy;
    }

    public StudentSearchReq() {
        super();
        // TODO Auto-generated constructor stub
    }

    public StudentSearchReq(String studentId, String name, String email, LocalDate birthdayStart, LocalDate birthdayEnd, LocalDateTime registrationTimeStart,
            LocalDateTime registrationTimeEnd, int activation, int subsidy) {
        super();
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.birthdayStart = birthdayStart;
        this.birthdayEnd = birthdayEnd;
        this.registrationTimeStart = registrationTimeStart;
        this.registrationTimeEnd = registrationTimeEnd;
        this.activation = activation;
        this.subsidy = subsidy;
    }

}
