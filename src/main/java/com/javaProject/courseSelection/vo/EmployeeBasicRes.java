package com.javaProject.courseSelection.vo;

public class EmployeeBasicRes {

    private String code;
    
    private String message;
    
    private String employeeId;
    
    private String name;
    
    private int authorizationRank;
    
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

    public boolean isActivation() {
        return activation;
    }

    public void setActivation(boolean activation) {
        this.activation = activation;
    }

    public EmployeeBasicRes() {
        super();
        // TODO Auto-generated constructor stub
    }

    public EmployeeBasicRes(String code, String message, String employeeId, String name, int authorizationRank, boolean activation) {
        super();
        this.code = code;
        this.message = message;
        this.employeeId = employeeId;
        this.name = name;
        this.authorizationRank = authorizationRank;
        this.activation = activation;
    }

    
}
