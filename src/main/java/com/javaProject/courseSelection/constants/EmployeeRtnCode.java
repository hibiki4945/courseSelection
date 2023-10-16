package com.javaProject.courseSelection.constants;

public enum EmployeeRtnCode {

    SUCCESSFUL("200", "successful!"),
    FAILED("400", "failed!"),
    EMPLOYEE_ID_EMPTY_ERROR("400", "EmployeeId Empty Error!"),
    EMPLOYEE_ID_OVER_LENGTH_ERROR("400", "EmployeeId OverLength Error!"),
    NAME_EMPTY_ERROR("400", "Name Empty Error!"),
    NAME_FORMAT_ERROR("400", "Name FORMAT Error!"),
    EMAIL_EMPTY_ERROR("400", "Email Empty Error!"),
    EMAIL_OVER_LENGTH_ERROR("400", "Email OverLength Error!"),
    EMAIL_FORMAT_ERROR("400", "Email Format Error!"),
    AUTHORIZATION_RANK_LESS_THEN_1_ERROR("400", "AuthorizationRank LessThan 1 Error!"),
    EMPLOYEE_ID_EXIST_ERROR("400", "EmployeeId Exist Error!"),
    EMAIL_EXIST_ERROR("400", "Email Exist Error!"),
    NOT_LOGIN_ERROR("400", "Please Login First!");
    
    private String code;
    
    private String message;

    private EmployeeRtnCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

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
    
}
