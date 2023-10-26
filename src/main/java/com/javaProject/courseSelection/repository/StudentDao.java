package com.javaProject.courseSelection.repository;

import com.javaProject.courseSelection.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StudentDao extends JpaRepository<Student, String>{

    public boolean existsByEmail(String Email);

    public List<Student> findByEmail(String Email);

    @Modifying
    @Transactional
    @Query(value = "update student s"
            + " set s.name = :inputName, "
            + " s.email = :inputEmail, "
            + " s.birthday = :inputBirthday "
            + " where s.student_id = :inputStudentId ", nativeQuery = true)
    public int change(@Param("inputStudentId") String studentId, 
                      @Param("inputName") String name,
                      @Param("inputEmail") String email,
                      @Param("inputBirthday") LocalDate birthday
                      );

//  搜尋學生(複合條件)
    @Query(value = "SELECT * FROM student where "
            + " student_id like case when :studentIdInput is null then '%%' else concat('%',:studentIdInput,'%') end and "
            + " name like case when :nameInput is null then '%%' else concat('%',:nameInput,'%') end and "
            + " email like case when :emailInput is null then '%%' else concat('%',:emailInput,'%') end and "
            + " birthday >= case when :birthdayStartInput is null then birthday else :birthdayStartInput end and "
            + " birthday <= case when :birthdayEndInput is null then birthday else :birthdayEndInput end and "
            + " registration_time >= case when :registrationTimeStartInput is null then registration_time else :registrationTimeStartInput end and "
            + " registration_time <= case when :registrationTimeEndInput is null then registration_time else :registrationTimeEndInput end and "
            + " activation = case when :activationInput != 0 && :activationInput != 1 then activation else :activationInput end and "
            + " subsidy = case when :subsidyInput != 0 && :subsidyInput != 1 then subsidy else :subsidyInput end "
            , nativeQuery = true)
    public Page<Student> search(
            Pageable pageable,
            @Param("studentIdInput") String studentId,
            @Param("nameInput") String name,
            @Param("emailInput") String email,
            @Param("birthdayStartInput") LocalDate birthdayStart,
            @Param("birthdayEndInput") LocalDate birthdayEnd,
            @Param("registrationTimeStartInput") LocalDateTime registrationTimeStart,
            @Param("registrationTimeEndInput") LocalDateTime registrationTimeEnd,
            @Param("activationInput") int activation,
            @Param("subsidyInput") int subsidy
            );

    @Modifying
    @Transactional
    public int removeByStudentId(String studentId);
    
}
