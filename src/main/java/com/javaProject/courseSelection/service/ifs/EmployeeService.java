package com.javaProject.courseSelection.service.ifs;

import com.javaProject.courseSelection.vo.EmployeeBasicRes;
import com.javaProject.courseSelection.vo.EmployeeChangePasswordReq;
import com.javaProject.courseSelection.vo.EmployeeChangeReq;
import com.javaProject.courseSelection.vo.EmployeeCreateReq;
import com.javaProject.courseSelection.vo.EmployeeDeleteReq;
import com.javaProject.courseSelection.vo.EmployeeFullRes;
import com.javaProject.courseSelection.vo.EmployeeSearchPageRes;
import com.javaProject.courseSelection.vo.EmployeeSearchReq;

import javax.servlet.http.HttpSession;

public interface EmployeeService {
/////////////////  人員登入API  /////////////////
//  人員 登入
    public EmployeeBasicRes Login(String employeeId, String password);
//  人員 登出
    public EmployeeBasicRes Logout(HttpSession httpSession);
//  人員 修改密碼
    public EmployeeBasicRes ChangePassword(EmployeeChangePasswordReq eChangePasswordReq);
//  人員 啟用帳號
    public EmployeeBasicRes Inactive(String employeeId);
//  人員 忘記密碼(寄驗證碼到信箱)
    public EmployeeBasicRes ForgetPassword(String employeeId);
//  人員 忘記密碼(寄驗證碼到信箱)
    public EmployeeBasicRes CheckToken(String token);
//  人員 重設密碼(for 忘記密碼)
    public EmployeeBasicRes ResetPassword(String employeeId, String inputToken, String newPassword, String newPasswordCheck);
//  確認 是否登入
    public EmployeeBasicRes LoginCheck();

/////////////////  人員管理API  /////////////////
//  新增 人員(權限>=100)
    public EmployeeBasicRes Create(EmployeeCreateReq createReq);
//  修改 人員(權限>=100)
    public EmployeeFullRes Change(EmployeeChangeReq changeReq);
//  查詢 人員(權限>=100 or 本人)
    public EmployeeSearchPageRes Search(EmployeeSearchReq searchReq);
//  刪除 人員(權限>=100)
    public EmployeeBasicRes Delete(EmployeeDeleteReq deleteReq);
    
}
