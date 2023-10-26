package com.javaProject.courseSelection.service.ifs;

import com.javaProject.courseSelection.vo.StudentBasicRes;
import com.javaProject.courseSelection.vo.StudentChangePasswordReq;
import com.javaProject.courseSelection.vo.StudentChangeReq;
import com.javaProject.courseSelection.vo.StudentCreateReq;
import com.javaProject.courseSelection.vo.StudentDeleteReq;
import com.javaProject.courseSelection.vo.StudentFullRes;
import com.javaProject.courseSelection.vo.StudentSearchPageRes;
import com.javaProject.courseSelection.vo.StudentSearchReq;

import javax.servlet.http.HttpSession;

public interface StudentService {
/////////////////  學生登入API  /////////////////
//  學生 登入
    public StudentBasicRes Login(String studentId, String password);
//  學生 登出
    public StudentBasicRes Logout(HttpSession httpSession);
//  學生 修改密碼
    public StudentBasicRes ChangePassword(StudentChangePasswordReq sChangePasswordReq);
//  學生 啟用帳號
    public StudentBasicRes Inactive(String studentId);
//  學生 忘記密碼(寄驗證碼到信箱)
    public StudentBasicRes ForgetPassword(String studentId);
//  學生 忘記密碼(寄驗證碼到信箱)
    public StudentBasicRes CheckToken(String token);
//  學生 重設密碼(for 忘記密碼)
    public StudentBasicRes ResetPassword(String studentId, String inputToken, String newPassword, String newPasswordCheck);
//  人員 查詢 學生的所有資訊
    public StudentFullRes CheckAllInfo(String studentId, String targetStudentId);
//  確認 是否登入
    public StudentBasicRes LoginCheck();

/////////////////  學生管理API  /////////////////
//  新增 學生(權限>=100)
    public StudentBasicRes Create(StudentCreateReq createReq);
//  修改 學生(權限>=100)
    public StudentFullRes Change(StudentChangeReq changeReq);
//  查詢 學生(權限>=1 or 本人)
    public StudentSearchPageRes Search(StudentSearchReq searchReq);
//  刪除 學生(權限>=100)
    public StudentBasicRes Delete(StudentDeleteReq deleteReq);
    
}
