package com.javaProject.courseSelection.repository;

import com.javaProject.courseSelection.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDao extends JpaRepository<Course, String>{

}
