package com.javaProject.courseSelection.vo;

public class StudentChangePasswordReq {

    private String studentId;
    
    private String oldPassword;
    
    private String newPassword;
    
    private String newPasswordCheck;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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

    public StudentChangePasswordReq() {
        super();
        // TODO Auto-generated constructor stub
    }

    public StudentChangePasswordReq(String studentId, String oldPassword, String newPassword, String newPasswordCheck) {
        super();
        this.studentId = studentId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
    }


}
