package com.javaProject.courseSelection.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeFullRes {

    private String code;
    
    private String message;
    
    private String employeeId;
    
    private String name;
    
    private int authorizationRank;
    
    private String email;

    private LocalDate birthday;

    private LocalDateTime registrationTime;
    
    private boolean activation;

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

    public EmployeeFullRes() {
        super();
        // TODO Auto-generated constructor stub
    }

    public EmployeeFullRes(String code, String message, String employeeId, String name, int authorizationRank, String email, LocalDate birthday, LocalDateTime registrationTime,
            boolean activation) {
        super();
        this.code = code;
        this.message = message;
        this.employeeId = employeeId;
        this.name = name;
        this.authorizationRank = authorizationRank;
        this.email = email;
        this.birthday = birthday;
        this.registrationTime = registrationTime;
        this.activation = activation;
    }

    
}
