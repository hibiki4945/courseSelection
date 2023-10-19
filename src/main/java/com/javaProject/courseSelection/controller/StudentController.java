package com.javaProject.courseSelection.controller;

import com.javaProject.courseSelection.service.ifs.StudentService;
import com.javaProject.courseSelection.vo.StudentBasicRes;
import com.javaProject.courseSelection.vo.StudentChangePasswordReq;
import com.javaProject.courseSelection.vo.StudentCheckTokenReq;
import com.javaProject.courseSelection.vo.StudentCreateReq;
import com.javaProject.courseSelection.vo.StudentLoginReq;
import com.javaProject.courseSelection.vo.StudentOnlyReq;
import com.javaProject.courseSelection.vo.StudentResetPasswordReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("student_api")
public class StudentController {

    @Autowired
    private StudentService sService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true") 
    @PostMapping("create")
    public StudentBasicRes Create(@RequestBody StudentCreateReq sCreateReq) {

        return sService.Create(sCreateReq);
        
    }
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true") 
    @PostMapping(value = "login")
    public StudentBasicRes Login(@RequestBody StudentLoginReq req, HttpSession httpSession) {

        StudentBasicRes res = sService.Login(req.getStudentId(), req.getPassword());
        if(!res.getCode().equals("200")) {
            return res;
        }

//      httpSession 暫存帳密
        System.out.printf("%s 登入, session_id: %s", req.getStudentId(), httpSession.getId());
        httpSession.setAttribute("account", req.getStudentId());
        httpSession.setAttribute("password", req.getPassword());
//      session 的預設存活時間是30分鐘
//      更改 session 的預設存活時間(為 60秒)
        httpSession.setMaxInactiveInterval(60);
        
        return res;
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true") 
    @PostMapping(value = "logout")
    public StudentBasicRes Logout(HttpSession httpSession) {

        return sService.Logout(httpSession);
        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true") 
    @PostMapping("change_password")
    public StudentBasicRes ChangePassword(@RequestBody StudentChangePasswordReq sChangePasswordReq) {

        return sService.ChangePassword(sChangePasswordReq);
        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true") 
    @PostMapping("inactive")
    public StudentBasicRes Inactive(@RequestBody StudentOnlyReq sOnlyReq) {

        return sService.Inactive(sOnlyReq.getStudentId());
        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true") 
    @PostMapping("forget_password")
    public StudentBasicRes ForgetPassword(@RequestBody StudentOnlyReq sOnlyReq) {
        
        return sService.ForgetPassword(sOnlyReq.getStudentId());
        
    }
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true") 
    @PostMapping("check_token")
    public StudentBasicRes CheckToken(@RequestBody StudentCheckTokenReq sCheckTokenReq) {
        
        return sService.CheckToken(sCheckTokenReq.getToken());
        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true") 
    @PostMapping("reset_password")
    public StudentBasicRes ResetPassword(@RequestBody StudentResetPasswordReq sResetPasswordReq) {
        
        return sService.ResetPassword(sResetPasswordReq.getStudentId(), sResetPasswordReq.getInputToken(), sResetPasswordReq.getNewPassword(), sResetPasswordReq.getNewPasswordCheck());
        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true") 
    @PostMapping("login_check")
    public StudentBasicRes LoginCheck() {
        
        return sService.LoginCheck();
        
    }
    
}
