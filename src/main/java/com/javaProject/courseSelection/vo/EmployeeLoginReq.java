package com.javaProject.courseSelection.vo;

public class EmployeeLoginReq {

    private String employeeId;

    private String password;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmployeeLoginReq() {
        super();
        // TODO Auto-generated constructor stub
    }

    public EmployeeLoginReq(String employeeId, String password) {
        super();
        this.employeeId = employeeId;
        this.password = password;
    }
    
}
