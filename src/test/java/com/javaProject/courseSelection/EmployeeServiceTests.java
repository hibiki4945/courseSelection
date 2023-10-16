package com.javaProject.courseSelection;

import com.javaProject.courseSelection.service.ifs.EmployeeService;
import com.javaProject.courseSelection.vo.EmployeeBasicRes;
import com.javaProject.courseSelection.vo.EmployeeCreateReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class EmployeeServiceTests {

    @Autowired
    private EmployeeService eService;
    
    @Test
    public void createTest() {

        LocalDate birthday = LocalDate.parse("1999-01-01");
        
        eService.Create(new EmployeeCreateReq("A001", "John", 1, "123@gmail.com", birthday));
        
    }
    
    @Test
    public void loginTest() {
        System.out.println("Login?");
        EmployeeBasicRes res = eService.Login("A001", "19990101");
        System.out.println(res.getMessage());
        res = eService.Login("A001", "19990100");
        System.out.println(res.getMessage());
        res = eService.Login("A001", "19990101");
        System.out.println(res.getMessage());
        System.out.println("Login all done!");
        
    }
    
}
