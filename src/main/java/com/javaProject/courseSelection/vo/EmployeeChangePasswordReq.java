package com.javaProject.courseSelection.vo;

public class EmployeeChangePasswordReq {

    private String employeeId;
    
    private String oldPassword;
    
    private String newPassword;
    
    private String newPasswordCheck;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public EmployeeChangePasswordReq() {
        super();
        // TODO Auto-generated constructor stub
    }

    public EmployeeChangePasswordReq(String employeeId, String oldPassword, String newPassword, String newPasswordCheck) {
        super();
        this.employeeId = employeeId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
    }


}
