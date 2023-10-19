package com.javaProject.courseSelection.vo;

public class StudentLoginReq {

    private String studentId;

    private String password;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StudentLoginReq() {
        super();
        // TODO Auto-generated constructor stub
    }

    public StudentLoginReq(String studentId, String password) {
        super();
        this.studentId = studentId;
        this.password = password;
    }

}
