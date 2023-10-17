package com.javaProject.courseSelection.vo;

public class EmployeeResetPasswordReq {

    private String employeeId;
    private String inputToken;
    private String newPassword;
    private String newPasswordCheck;
    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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
    public EmployeeResetPasswordReq() {
        super();
        // TODO Auto-generated constructor stub
    }
    public EmployeeResetPasswordReq(String employeeId, String inputToken, String newPassword, String newPasswordCheck) {
        super();
        this.employeeId = employeeId;
        this.inputToken = inputToken;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
    }
    
}
