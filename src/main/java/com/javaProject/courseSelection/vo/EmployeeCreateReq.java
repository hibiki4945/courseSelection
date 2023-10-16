package com.javaProject.courseSelection.vo;

import java.time.LocalDate;

public class EmployeeCreateReq {

    private String employeeId;
    
    private String name;
    
    private int authorizationRank;
    
    private String email;
    
    private LocalDate birthday;

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

    public EmployeeCreateReq() {
        super();
        // TODO Auto-generated constructor stub
    }

    public EmployeeCreateReq(String employeeId, String name, int authorizationRank, String email, LocalDate birthday) {
        super();
        this.employeeId = employeeId;
        this.name = name;
        this.authorizationRank = authorizationRank;
        this.email = email;
        this.birthday = birthday;
    }

    
}
