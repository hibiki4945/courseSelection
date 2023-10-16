package com.javaProject.courseSelection.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
public class Employee {
    
    @Id
    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "name")
    private String name;
    
    @Column(name = "password")
    private String password;

    @Column(name = "authorization_rank")
    private int authorizationRank;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "registration_time")
    private LocalDateTime registrationTime;

    @Column(name = "activation")
    private boolean activation;


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public int getAuthorizationRank() {
        return authorizationRank;
    }

    public void setAuthorizationRank(int authorizationRank) {
        this.authorizationRank = authorizationRank;
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

    public Employee() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Employee(String employeeId, String name, String password, int authorizationRank, String email, LocalDate birthday, LocalDateTime registrationTime, boolean activation) {
        super();
        this.employeeId = employeeId;
        this.name = name;
        this.password = password;
        this.authorizationRank = authorizationRank;
        this.email = email;
        this.birthday = birthday;
        this.registrationTime = registrationTime;
        this.activation = activation;
    }

    
}
