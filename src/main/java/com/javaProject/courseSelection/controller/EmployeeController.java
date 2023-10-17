package com.javaProject.courseSelection.controller;

import com.javaProject.courseSelection.service.ifs.EmployeeService;
import com.javaProject.courseSelection.vo.EmployeeBasicRes;
import com.javaProject.courseSelection.vo.EmployeeChangePasswordReq;
import com.javaProject.courseSelection.vo.EmployeeCreateReq;
import com.javaProject.courseSelection.vo.EmployeeLoginReq;
import com.javaProject.courseSelection.vo.EmployeeOnlyReq;
import com.javaProject.courseSelection.vo.EmployeeResetPasswordReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("employee_api")
public class EmployeeController {

    @Autowired
    private EmployeeService eService;

    @PostMapping("create")
    public EmployeeBasicRes Create(@RequestBody EmployeeCreateReq eCreateReq) {

        return eService.Create(eCreateReq);
        
    }

    @PostMapping(value = "login")
    public EmployeeBasicRes Login(@RequestBody EmployeeLoginReq req, HttpSession httpSession) {

        EmployeeBasicRes res = eService.Login(req.getEmployeeId(), req.getPassword());
        if(!res.getCode().equals("200")) {
            return res;
        }

//      httpSession 暫存帳密
        System.out.printf("%s 登入, session_id: %s", req.getEmployeeId(), httpSession.getId());
        httpSession.setAttribute("account", req.getEmployeeId());
        httpSession.setAttribute("password", req.getPassword());
//      session 的預設存活時間是30分鐘
//      更改 session 的預設存活時間(為 60秒)
        httpSession.setMaxInactiveInterval(60);
        
        return res;
    }

    @PostMapping(value = "logout")
    public EmployeeBasicRes Logout(HttpSession httpSession) {

        return eService.Logout(httpSession);
        
    }

    @PostMapping("change_password")
    public EmployeeBasicRes ChangePassword(@RequestBody EmployeeChangePasswordReq eChangePasswordReq) {

        return eService.ChangePassword(eChangePasswordReq);
        
    }
    
    @PostMapping("inactive")
    public EmployeeBasicRes Inactive(@RequestBody EmployeeOnlyReq eOnlyReq) {

        return eService.Inactive(eOnlyReq.getEmployeeId());
        
    }
    
    @PostMapping("forget_password")
    public EmployeeBasicRes ForgetPassword(@RequestBody EmployeeOnlyReq eOnlyReq) {
        
        return eService.ForgetPassword(eOnlyReq.getEmployeeId());
        
    }
    
    @PostMapping("reset_password")
    public EmployeeBasicRes ResetPassword(@RequestBody EmployeeResetPasswordReq eResetPasswordReq) {
        
        return eService.ResetPassword(eResetPasswordReq.getEmployeeId(), eResetPasswordReq.getInputToken(), eResetPasswordReq.getNewPassword(), eResetPasswordReq.getNewPasswordCheck());
        
    }
    
}
