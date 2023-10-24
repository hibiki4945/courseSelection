package com.javaProject.courseSelection.repository;

import com.javaProject.courseSelection.entity.Employee;
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

public interface EmployeeDao extends JpaRepository<Employee, String>{

    public boolean existsByEmail(String Email);
    
    public List<Employee> findByEmail(String Email);

    @Modifying
    @Transactional
    @Query(value = "update employee e"
            + " set e.name = :inputName, "
            + " e.authorization_rank = :inputAuthorizationRank, "
            + " e.email = :inputEmail, "
            + " e.birthday = :inputBirthday "
            + " where e.employee_id = :inputEmployeeId ", nativeQuery = true)
    public int change(@Param("inputEmployeeId") String employeeId, 
                      @Param("inputName") String name,
                      @Param("inputAuthorizationRank") int authorizationRank,
                      @Param("inputEmail") String email,
                      @Param("inputBirthday") LocalDate birthday
                      );

//  搜尋人員(複合條件)
    @Query(value = "SELECT * FROM employee where "
            + " employee_id like case when :employeeIdInput is null then '%%' else concat('%',:employeeIdInput,'%') end and "
            + " name like case when :nameInput is null then '%%' else concat('%',:nameInput,'%') end and "
            + " authorization_rank = case when :authorizationRankInput <= 0 then authorization_rank else :authorizationRankInput end and "
            + " email like case when :emailInput is null then '%%' else concat('%',:emailInput,'%') end and "
            + " birthday >= case when :birthdayStartInput is null then birthday else :birthdayStartInput end and "
            + " birthday <= case when :birthdayEndInput is null then birthday else :birthdayEndInput end and "
            + " registration_time >= case when :registrationTimeStartInput is null then registration_time else :registrationTimeStartInput end and "
            + " registration_time <= case when :registrationTimeEndInput is null then registration_time else :registrationTimeEndInput end and "
            + " activation = case when :activationInput != 0 && :activationInput != 1 then activation else :activationInput end "
            , nativeQuery = true)
    public Page<Employee> search(
            Pageable pageable,
            @Param("employeeIdInput") String employeeId,
            @Param("nameInput") String name,
            @Param("authorizationRankInput") int authorizationRank,
            @Param("emailInput") String email,
            @Param("birthdayStartInput") LocalDate birthdayStart,
            @Param("birthdayEndInput") LocalDate birthdayEnd,
            @Param("registrationTimeStartInput") LocalDateTime registrationTimeStart,
            @Param("registrationTimeEndInput") LocalDateTime registrationTimeEnd,
            @Param("activationInput") int activation
            );

    @Modifying
    @Transactional
    public int removeByEmployeeId(String employeeId);
    
}
