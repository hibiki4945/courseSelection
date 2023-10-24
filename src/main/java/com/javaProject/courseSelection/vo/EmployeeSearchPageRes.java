package com.javaProject.courseSelection.vo;

import com.javaProject.courseSelection.entity.Employee;
import org.springframework.data.domain.Page;

public class EmployeeSearchPageRes {

    private String code;
    
    private String message;
    
    private Page<Employee> employeePage;

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

    public Page<Employee> getEmployeePage() {
        return employeePage;
    }

    public void setEmployeePage(Page<Employee> employeePage) {
        this.employeePage = employeePage;
    }

    public EmployeeSearchPageRes() {
        super();
        // TODO Auto-generated constructor stub
    }

    public EmployeeSearchPageRes(String code, String message, Page<Employee> employeePage) {
        super();
        this.code = code;
        this.message = message;
        this.employeePage = employeePage;
    }
    
}
