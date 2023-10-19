package com.javaProject.courseSelection.constants;

public enum StudentRtnCode {

    SUCCESSFUL("200", "successful!"),
    FAILED("400", "failed!"),
    STUDENT_ID_EMPTY_ERROR("400", "StudentId Empty Error!"),
    STUDENT_ID_FORMAT_ERROR("400", "StudentId Format Error!"),
    NAME_EMPTY_ERROR("400", "Name Empty Error!"),
    NAME_FORMAT_ERROR("400", "Name FORMAT Error!"),
    EMAIL_EMPTY_ERROR("400", "Email Empty Error!"),
    EMAIL_OVER_LENGTH_ERROR("400", "Email OverLength Error!"),
    EMAIL_FORMAT_ERROR("400", "Email Format Error!"),
    AUTHORIZATION_RANK_LESS_THEN_1_ERROR("400", "AuthorizationRank LessThan 1 Error!"),
    STUDENT_ID_EXIST_ERROR("400", "StudentId Exist Error!"),
    STUDENT_ID_NOT_EXIST_ERROR("400", "StudentId Not Exist Error!"),
    EMAIL_EXIST_ERROR("400", "Email Exist Error!"),
    NOT_LOGIN_ERROR("400", "Please Login First!"),
    OLD_PASSWORD_EMPTY_ERROR("400", "OldPassword Empty Error!"),
    OLD_PASSWORD_FORMAT_ERROR("400", "OldPassword Format Error!"),
    NEW_PASSWORD_EMPTY_ERROR("400", "NewPassword Empty Error!"),
    NEW_PASSWORD_FORMAT_ERROR("400", "NewPassword Format Error!"),
    NEW_PASSWORD_NOT_CHANGE_ERROR("400", "NewPassword Not Change Error!"),
    NEW_PASSWORD_CHECK_EMPTY_ERROR("400", "NewPasswordCheck Empty Error!"),
    NEW_PASSWORD_CHECK_FORMAT_ERROR("400", "NewPasswordCheck Format Error!"),
    NEW_PASSWORD_CHECK_NOT_EQUAL_ERROR("400", "NewPasswordCheck Not Equal Error!"),
    PASSWORD_ERROR("400", "Password Error!"),
    DAO_ERROR("400", "Dao Error!"),
    TOKEN_ERROR("400", "Token Error!");
    
    private String code;
    
    private String message;

    private StudentRtnCode(String code, String message) {
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
