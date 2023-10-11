package com.javaProject.courseSelection.repository;

import com.javaProject.courseSelection.entity.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseScheduleDao extends JpaRepository<CourseSchedule, Integer>{
    
}
