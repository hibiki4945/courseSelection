package com.javaProject.courseSelection.vo;

public class EmployeeChangePasswordReq {

    private String employeeId;
    
    private String oldPassWord;
    
    private String newPassWord;
    
    private String newPassWordCheck;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getOldPassWord() {
        return oldPassWord;
    }

    public void setOldPassWord(String oldPassWord) {
        this.oldPassWord = oldPassWord;
    }

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }

    public String getNewPassWordCheck() {
        return newPassWordCheck;
    }

    public void setNewPassWordCheck(String newPassWordCheck) {
        this.newPassWordCheck = newPassWordCheck;
    }

    public EmployeeChangePasswordReq() {
        super();
        // TODO Auto-generated constructor stub
    }

    public EmployeeChangePasswordReq(String employeeId, String oldPassWord, String newPassWord, String newPassWordCheck) {
        super();
        this.employeeId = employeeId;
        this.oldPassWord = oldPassWord;
        this.newPassWord = newPassWord;
        this.newPassWordCheck = newPassWordCheck;
    }

}
