package com.javaProject.courseSelection.vo;

public class StudentResetPasswordReq {

    private String studentId;
    
    private String inputToken;
    
    private String newPassword;
    
    private String newPasswordCheck;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getInputToken() {
        return inputToken;
    }

    public void setInputToken(String inputToken) {
        this.inputToken = inputToken;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordCheck() {
        return newPasswordCheck;
    }

    public void setNewPasswordCheck(String newPasswordCheck) {
        this.newPasswordCheck = newPasswordCheck;
    }

    public StudentResetPasswordReq() {
        super();
        // TODO Auto-generated constructor stub
    }

    public StudentResetPasswordReq(String studentId, String inputToken, String newPassword, String newPasswordCheck) {
        super();
        this.studentId = studentId;
        this.inputToken = inputToken;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
    }
    
}
