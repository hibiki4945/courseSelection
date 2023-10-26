package com.javaProject.courseSelection.vo;

import com.javaProject.courseSelection.entity.Student;
import org.springframework.data.domain.Page;

public class StudentSearchPageRes {

    private String code;
    
    private String message;
    
    private Page<Student> studentPage;

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

    public Page<Student> getStudentPage() {
        return studentPage;
    }

    public void setStudentPage(Page<Student> studentPage) {
        this.studentPage = studentPage;
    }

    public StudentSearchPageRes() {
        super();
        // TODO Auto-generated constructor stub
    }

    public StudentSearchPageRes(String code, String message, Page<Student> studentPage) {
        super();
        this.code = code;
        this.message = message;
        this.studentPage = studentPage;
    }

}
