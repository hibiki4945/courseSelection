package com.javaProject.courseSelection.repository;

import com.javaProject.courseSelection.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao extends JpaRepository<Employee, String>{

}
