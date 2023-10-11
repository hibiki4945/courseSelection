package com.javaProject.courseSelection.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "student")
public class Student {
    
    @Id
    @Column(name = "student_number")
    private String studentNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;
    
    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "registration_time")
    private LocalDateTime registrationTime;

    @Column(name = "activation")
    private boolean activation;

    @Column(name = "subsidy")
    private boolean subsidy;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Student() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Student(String studentNumber, String name, String password, String email, LocalDate birthday, LocalDateTime registrationTime, boolean activation, boolean subsidy) {
        super();
        this.studentNumber = studentNumber;
        this.name = name;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.registrationTime = registrationTime;
        this.activation = activation;
        this.subsidy = subsidy;
    }
    
}
