package com.javaProject.courseSelection.service.ifs;

import com.javaProject.courseSelection.vo.EmployeeBasicRes;
import com.javaProject.courseSelection.vo.EmployeeChangePasswordReq;
import com.javaProject.courseSelection.vo.EmployeeCreateReq;
import com.javaProject.courseSelection.vo.EmployeeFullRes;

import javax.servlet.http.HttpSession;

public interface EmployeeService {
//  新增 人員
    public EmployeeBasicRes Create(EmployeeCreateReq createReq);
//  人員 登入
    public EmployeeBasicRes Login(String EmployeeId, String Password);
//  人員 登出
    public EmployeeBasicRes Logout(HttpSession httpSession);
//  人員 修改密碼
    public EmployeeBasicRes ChangePassword(EmployeeChangePasswordReq eChangePasswordReq);
//  人員 啟用帳號
    public EmployeeBasicRes Inactive(String EmployeeId);
//  人員 忘記密碼(寄驗證碼到信箱)
    public EmployeeBasicRes ForgetPassword(String EmployeeId);
//  人員 重設密碼(for 忘記密碼)
    public EmployeeBasicRes ResetPassword(String EmployeeId, String NewPassword, String NewPasswordCheck);
//  人員 查詢 (其它)人員的所有資訊
    public EmployeeFullRes CheckAllInfo(String EmployeeId, String TargetEmployeeId);
    
}
