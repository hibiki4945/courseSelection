package com.javaProject.courseSelection.vo;

public class StudentOnlyReq {
    
    private String studentId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public StudentOnlyReq() {
        super();
        // TODO Auto-generated constructor stub
    }

    public StudentOnlyReq(String studentId) {
        super();
        this.studentId = studentId;
    }

}
