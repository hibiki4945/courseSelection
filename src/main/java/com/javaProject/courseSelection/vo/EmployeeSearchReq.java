package com.javaProject.courseSelection.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeSearchReq {

    private String employeeId;
    
    private String name;
    
    private int authorizationRank;
    
    private String email;

    private LocalDate birthdayStart;
    
    private LocalDate birthdayEnd;

    private LocalDateTime registrationTimeStart;
    
    private LocalDateTime registrationTimeEnd;
    
    private int activation;

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

    public EmployeeSearchReq() {
        super();
        // TODO Auto-generated constructor stub
    }

    public EmployeeSearchReq(String employeeId, String name, int authorizationRank, String email, LocalDate birthdayStart, LocalDate birthdayEnd,
            LocalDateTime registrationTimeStart, LocalDateTime registrationTimeEnd, int activation) {
        super();
        this.employeeId = employeeId;
        this.name = name;
        this.authorizationRank = authorizationRank;
        this.email = email;
        this.birthdayStart = birthdayStart;
        this.birthdayEnd = birthdayEnd;
        this.registrationTimeStart = registrationTimeStart;
        this.registrationTimeEnd = registrationTimeEnd;
        this.activation = activation;
    }

}
