package com.javaProject.courseSelection.repository;

import com.javaProject.courseSelection.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDao extends JpaRepository<Student, String>{

    public boolean existsByEmail(String Email);
    
}
