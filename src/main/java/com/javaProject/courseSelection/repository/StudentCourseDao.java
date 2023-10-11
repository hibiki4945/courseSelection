package com.javaProject.courseSelection.repository;

import com.javaProject.courseSelection.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseDao extends JpaRepository<StudentCourse, String>{
    
}
