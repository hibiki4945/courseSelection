package com.javaProject.courseSelection.vo;

public class StudentBasicRes {

    private String code;
    
    private String message;
    
    private String studentId;
    
    private String name;
    
//    private int authorizationRank;
    
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

    public boolean isActivation() {
        return activation;
    }

    public void setActivation(boolean activation) {
        this.activation = activation;
    }

    public StudentBasicRes() {
        super();
        // TODO Auto-generated constructor stub
    }

    public StudentBasicRes(String code, String message, String studentId, String name, boolean activation) {
        super();
        this.code = code;
        this.message = message;
        this.studentId = studentId;
        this.name = name;
        this.activation = activation;
    }

}
