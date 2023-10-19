package com.javaProject.courseSelection.service.impl;

import com.javaProject.courseSelection.constants.StudentRtnCode;
import com.javaProject.courseSelection.entity.Student;
import com.javaProject.courseSelection.repository.StudentDao;
import com.javaProject.courseSelection.service.ifs.StudentService;
import com.javaProject.courseSelection.vo.StudentBasicRes;
import com.javaProject.courseSelection.vo.StudentChangePasswordReq;
import com.javaProject.courseSelection.vo.StudentCreateReq;
import com.javaProject.courseSelection.vo.StudentFullRes;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{
//  log
    private Logger logger = LoggerFactory.getLogger(getClass());  
    
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//    private String forgetPasswordStudentId; 
    private String forgetPasswordToken; 
    
    @Autowired
    private StudentDao sDao;

    @Autowired
    private JavaMailSender mailSender;
    
    private String studentIdPattern = "^.{2,10}$";
    private String namePattern = "^.{2,30}$";
    private String pwdPattern = "^.{8,30}$";
    private String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    
    @Override
    public StudentBasicRes Create(StudentCreateReq createReq0) {

        logger.info("Student's Create Start!");
        
//      變數的本地化
        StudentCreateReq createReq = null;
        createReq = createReq0;

        if(!StringUtils.hasText(createReq.getStudentId())) {
            return new StudentBasicRes(StudentRtnCode.STUDENT_ID_EMPTY_ERROR.getCode(), StudentRtnCode.STUDENT_ID_EMPTY_ERROR.getMessage(), createReq.getStudentId(), createReq.getName(), false);
        }
        if(!createReq.getStudentId().matches(studentIdPattern)) {
            return new StudentBasicRes(StudentRtnCode.STUDENT_ID_FORMAT_ERROR.getCode(), StudentRtnCode.STUDENT_ID_FORMAT_ERROR.getMessage(), createReq.getStudentId(), createReq.getName(), false);
        }
        if(!StringUtils.hasText(createReq.getName())) {
            return new StudentBasicRes(StudentRtnCode.NAME_EMPTY_ERROR.getCode(), StudentRtnCode.NAME_EMPTY_ERROR.getMessage(), createReq.getStudentId(), createReq.getName(), false);
        }
        if(!createReq.getName().matches(namePattern)) {
            return new StudentBasicRes(StudentRtnCode.NAME_FORMAT_ERROR.getCode(), StudentRtnCode.NAME_FORMAT_ERROR.getMessage(), createReq.getStudentId(), createReq.getName(), false);
        }
        if(!StringUtils.hasText(createReq.getEmail())) {
            return new StudentBasicRes(StudentRtnCode.EMAIL_EMPTY_ERROR.getCode(), StudentRtnCode.EMAIL_EMPTY_ERROR.getMessage(), createReq.getStudentId(), createReq.getName(), false);
        }
        if(createReq.getEmail().length() > 40) {
            return new StudentBasicRes(StudentRtnCode.EMAIL_OVER_LENGTH_ERROR.getCode(), StudentRtnCode.EMAIL_OVER_LENGTH_ERROR.getMessage(), createReq.getStudentId(), createReq.getName(), false);
        }
        if(!createReq.getEmail().matches(emailPattern)) {
            return new StudentBasicRes(StudentRtnCode.EMAIL_FORMAT_ERROR.getCode(), StudentRtnCode.EMAIL_FORMAT_ERROR.getMessage(), createReq.getStudentId(), createReq.getName(), false);
        }
//        if(createReq.getAuthorizationRank() < 1) {
//            return new EmployeeBasicRes(EmployeeRtnCode.AUTHORIZATION_RANK_LESS_THEN_1_ERROR.getCode(), EmployeeRtnCode.AUTHORIZATION_RANK_LESS_THEN_1_ERROR.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
//        }
//        if(createReq.getBirthday()) {
//            
//        }
        if(sDao.existsById(createReq.getStudentId())) {
            return new StudentBasicRes(StudentRtnCode.STUDENT_ID_EXIST_ERROR.getCode(), StudentRtnCode.STUDENT_ID_EXIST_ERROR.getMessage(), createReq.getStudentId(), createReq.getName(), false);
        }
        if(sDao.existsByEmail(createReq.getEmail())) {
            return new StudentBasicRes(StudentRtnCode.EMAIL_EXIST_ERROR.getCode(), StudentRtnCode.EMAIL_EXIST_ERROR.getMessage(), createReq.getStudentId(), createReq.getName(), false);
        }
        
//        String randomPwd = RandomString.make(10);
        
        Student student = new Student();
        student.setStudentId(createReq.getStudentId());
        student.setName(createReq.getName());
        student.setPassword(encoder.encode(createReq.getBirthday().toString().replace("-", "")));
        student.setEmail(createReq.getEmail());
        student.setBirthday(createReq.getBirthday());
        student.setRegistrationTime(LocalDateTime.now());
        student.setSubsidy(false);
        
        sDao.save(student);

        logger.info("Student's Create Done!");
//        return new EmployeeFullRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), createReq.getEmployeeId(), createReq.getName(), createReq.getAuthorizationRank(), createReq.getEmail(), createReq.getBirthday(), employee.getRegistrationTime(), false);
        return new StudentBasicRes(StudentRtnCode.SUCCESSFUL.getCode(), StudentRtnCode.SUCCESSFUL.getMessage(), createReq.getStudentId(), createReq.getName(), false);
        
    }

    @Cacheable(cacheNames = "student_addInfo", key = "#studentId", unless = "#result.code != '200'")
    @Override
    public StudentBasicRes Login(String studentId, String password) {
        System.out.println("Login!");
        Optional<Student> res0 = sDao.findById(studentId);
        if(!res0.isPresent()) {
            System.out.println("!res0.isPresent()");
//          記得返回 錯誤訊息
            return new StudentBasicRes(StudentRtnCode.STUDENT_ID_NOT_EXIST_ERROR.getCode(), StudentRtnCode.STUDENT_ID_NOT_EXIST_ERROR.getMessage(), null, null, false);
        }
        Student res = res0.get();
        
        if(!encoder.matches(password, res.getPassword())) {
//          記得返回 錯誤訊息
            return new StudentBasicRes(StudentRtnCode.PASSWORD_ERROR.getCode(), StudentRtnCode.PASSWORD_ERROR.getMessage(), null, null, false);
        }
        
        System.out.println("login successed!");
        
        return new StudentBasicRes(StudentRtnCode.SUCCESSFUL.getCode(), StudentRtnCode.SUCCESSFUL.getMessage(), res.getStudentId(), res.getName(), res.isActivation());
    
    } 

    @Override
    public StudentBasicRes Logout(HttpSession httpSession) {

        httpSession.invalidate();
        
        return new StudentBasicRes(StudentRtnCode.SUCCESSFUL.getCode(), StudentRtnCode.SUCCESSFUL.getMessage(), null, null, false);
        
    }

    @Override
    public StudentBasicRes ChangePassword(StudentChangePasswordReq sChangePasswordReq) {
//      變數的本地化
        String studentId = sChangePasswordReq.getStudentId();
        String oldPassword = sChangePasswordReq.getOldPassword();
        String newPassword = sChangePasswordReq.getNewPassword();
        String newPasswordCheck = sChangePasswordReq.getNewPasswordCheck();

        if(!StringUtils.hasText(studentId)) {
            return new StudentBasicRes(StudentRtnCode.STUDENT_ID_EMPTY_ERROR.getCode(), StudentRtnCode.STUDENT_ID_EMPTY_ERROR.getMessage(), studentId, null, false);
        }
        if(!studentId.matches(studentIdPattern)) {
            return new StudentBasicRes(StudentRtnCode.STUDENT_ID_FORMAT_ERROR.getCode(), StudentRtnCode.STUDENT_ID_FORMAT_ERROR.getMessage(), studentId, null, false);
        }
        if(!StringUtils.hasText(oldPassword)) {
            return new StudentBasicRes(StudentRtnCode.OLD_PASSWORD_EMPTY_ERROR.getCode(), StudentRtnCode.OLD_PASSWORD_EMPTY_ERROR.getMessage(), studentId, null, false);
        }
        if(!oldPassword.matches(pwdPattern)) {
            return new StudentBasicRes(StudentRtnCode.OLD_PASSWORD_FORMAT_ERROR.getCode(), StudentRtnCode.OLD_PASSWORD_FORMAT_ERROR.getMessage(), studentId, null, false);
        }
        if(!StringUtils.hasText(newPassword)) {
            return new StudentBasicRes(StudentRtnCode.NEW_PASSWORD_EMPTY_ERROR.getCode(), StudentRtnCode.NEW_PASSWORD_EMPTY_ERROR.getMessage(), studentId, null, false);
        }
        if(!newPassword.matches(pwdPattern)) {
            return new StudentBasicRes(StudentRtnCode.NEW_PASSWORD_FORMAT_ERROR.getCode(), StudentRtnCode.NEW_PASSWORD_FORMAT_ERROR.getMessage(), studentId, null, false);
        }
        if(newPassword.matches(oldPassword)) {
            return new StudentBasicRes(StudentRtnCode.NEW_PASSWORD_NOT_CHANGE_ERROR.getCode(), StudentRtnCode.NEW_PASSWORD_NOT_CHANGE_ERROR.getMessage(), studentId, null, false);
        }
        if(!StringUtils.hasText(newPasswordCheck)) {
            return new StudentBasicRes(StudentRtnCode.NEW_PASSWORD_CHECK_EMPTY_ERROR.getCode(), StudentRtnCode.NEW_PASSWORD_CHECK_EMPTY_ERROR.getMessage(), studentId, null, false);
        }
        if(!newPasswordCheck.matches(pwdPattern)) {
            return new StudentBasicRes(StudentRtnCode.NEW_PASSWORD_CHECK_FORMAT_ERROR.getCode(), StudentRtnCode.NEW_PASSWORD_CHECK_FORMAT_ERROR.getMessage(), studentId, null, false);
        }
        if(!newPassword.matches(newPasswordCheck)) {
            return new StudentBasicRes(StudentRtnCode.NEW_PASSWORD_CHECK_NOT_EQUAL_ERROR.getCode(), StudentRtnCode.NEW_PASSWORD_CHECK_NOT_EQUAL_ERROR.getMessage(), studentId, null, false);
        }
        Student res0 = sDao.findById(studentId).get();
        if(res0 == null) {
            return new StudentBasicRes(StudentRtnCode.STUDENT_ID_NOT_EXIST_ERROR.getCode(), StudentRtnCode.STUDENT_ID_NOT_EXIST_ERROR.getMessage(), studentId, null, false);
        }
        if(encoder.encode(oldPassword).matches(res0.getPassword())) {
            return new StudentBasicRes(StudentRtnCode.PASSWORD_ERROR.getCode(), StudentRtnCode.PASSWORD_ERROR.getMessage(), studentId, null, false);
        }

        res0.setPassword(encoder.encode(newPassword));
        Student res = sDao.save(res0);
        if(res == null) {
            return new StudentBasicRes(StudentRtnCode.DAO_ERROR.getCode(), StudentRtnCode.DAO_ERROR.getMessage(), studentId, null, false);
        }
        
        if(!res0.isActivation()) {
            Inactive(res0.getStudentId());
        } 
        
        return new StudentBasicRes(StudentRtnCode.SUCCESSFUL.getCode(), StudentRtnCode.SUCCESSFUL.getMessage(), studentId, null, false);
         
    }

    @Override
    public StudentBasicRes Inactive(String studentId) {

        Student res0 = sDao.findById(studentId).get();
        if(!res0.isActivation()) {
            res0.setActivation(true);
        }
        Student res = sDao.save(res0);
        if(res == null) {
            return new StudentBasicRes(StudentRtnCode.DAO_ERROR.getCode(), StudentRtnCode.DAO_ERROR.getMessage(), studentId, null, false);
        }
        
        return new StudentBasicRes(StudentRtnCode.SUCCESSFUL.getCode(), StudentRtnCode.SUCCESSFUL.getMessage(), studentId, null, false);
        
    }

    @Override
    public StudentBasicRes ForgetPassword(String studentId) {

        if(!StringUtils.hasText(studentId)) {
            return new StudentBasicRes(StudentRtnCode.STUDENT_ID_EMPTY_ERROR.getCode(), StudentRtnCode.STUDENT_ID_EMPTY_ERROR.getMessage(), studentId, null, false);
        }
        if(!studentId.matches(studentIdPattern)) {
            return new StudentBasicRes(StudentRtnCode.STUDENT_ID_FORMAT_ERROR.getCode(), StudentRtnCode.STUDENT_ID_FORMAT_ERROR.getMessage(), studentId, null, false);
        }
        Student res = sDao.findById(studentId).get();
        if(res == null) {
            return new StudentBasicRes(StudentRtnCode.STUDENT_ID_NOT_EXIST_ERROR.getCode(), StudentRtnCode.STUDENT_ID_NOT_EXIST_ERROR.getMessage(), studentId, null, false);
        }
        String email = res.getEmail();
//        System.out.println("email: "+email);
        
        forgetPasswordToken = RandomString.make(20);
        System.out.println("forgetPasswordToken: "+forgetPasswordToken);
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("java12629@gmail.com", "xx選課系統");
            helper.setTo(email);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String subject = "已要求重新設定密碼";
        String content = "<p>你好, </p>" + "<p>您已要求重新設定密碼</p>" + "<p>驗證碼:</p>" + "<p>" + forgetPasswordToken + "</p>" + "<br>"
                + "<p>驗證碼有效時間為10分鐘</p>" + "<p>感謝您利用xx選課系統</p>";

        try {
            helper.setSubject(subject);
            helper.setText(content, true);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // send to user email
        mailSender.send(message);
        
        return new StudentBasicRes(StudentRtnCode.SUCCESSFUL.getCode(), StudentRtnCode.SUCCESSFUL.getMessage(), studentId, null, false);
        
    }

    @Override
    public StudentBasicRes CheckToken(String token) {

        if(!StringUtils.hasText(token)) {
            return new StudentBasicRes(StudentRtnCode.TOKEN_ERROR.getCode(), StudentRtnCode.TOKEN_ERROR.getMessage(), null, null, false);
        }
        if(!token.matches(forgetPasswordToken)) {
            return new StudentBasicRes(StudentRtnCode.TOKEN_ERROR.getCode(), StudentRtnCode.TOKEN_ERROR.getMessage(), null, null, false);
        }
        
        return new StudentBasicRes(StudentRtnCode.SUCCESSFUL.getCode(), StudentRtnCode.SUCCESSFUL.getMessage(), null, null, false);
        
    }

    @Override
    public StudentBasicRes ResetPassword(String studentId0, String inputToken0, String newPassword0, String newPasswordCheck0) {

//      變數的本地化
        String studentId = studentId0;
        String inputToken = inputToken0;
        String newPassword = newPassword0;
        String newPasswordCheck = newPasswordCheck0;

        if(!StringUtils.hasText(studentId)) {
            return new StudentBasicRes(StudentRtnCode.STUDENT_ID_EMPTY_ERROR.getCode(), StudentRtnCode.STUDENT_ID_EMPTY_ERROR.getMessage(), studentId, null, false);
        }
        if(!studentId.matches(studentIdPattern)) {
            return new StudentBasicRes(StudentRtnCode.STUDENT_ID_FORMAT_ERROR.getCode(), StudentRtnCode.STUDENT_ID_FORMAT_ERROR.getMessage(), studentId, null, false);
        }
        if(!inputToken.matches(forgetPasswordToken)) {
            return new StudentBasicRes(StudentRtnCode.TOKEN_ERROR.getCode(), StudentRtnCode.TOKEN_ERROR.getMessage(), studentId, null, false);
        }
        if(!newPassword.matches(pwdPattern)) {
            return new StudentBasicRes(StudentRtnCode.NEW_PASSWORD_FORMAT_ERROR.getCode(), StudentRtnCode.NEW_PASSWORD_FORMAT_ERROR.getMessage(), studentId, null, false);
        }
        if(!newPasswordCheck.matches(pwdPattern)) {
            return new StudentBasicRes(StudentRtnCode.NEW_PASSWORD_CHECK_FORMAT_ERROR.getCode(), StudentRtnCode.NEW_PASSWORD_CHECK_FORMAT_ERROR.getMessage(), studentId, null, false);
        }
        if(!newPassword.matches(newPasswordCheck)) {
            return new StudentBasicRes(StudentRtnCode.NEW_PASSWORD_CHECK_NOT_EQUAL_ERROR.getCode(), StudentRtnCode.NEW_PASSWORD_CHECK_NOT_EQUAL_ERROR.getMessage(), studentId, null, false);
        }
        Student res0 = sDao.findById(studentId).get();
        if(res0 == null) {
            return new StudentBasicRes(StudentRtnCode.STUDENT_ID_NOT_EXIST_ERROR.getCode(), StudentRtnCode.STUDENT_ID_NOT_EXIST_ERROR.getMessage(), studentId, null, false);
        }

        res0.setPassword(encoder.encode(newPassword));
        Student res = sDao.save(res0);
        if(res == null) {
            return new StudentBasicRes(StudentRtnCode.DAO_ERROR.getCode(), StudentRtnCode.DAO_ERROR.getMessage(), studentId, null, false);
        } 

        forgetPasswordToken = RandomString.make(20);
        
        return new StudentBasicRes(StudentRtnCode.SUCCESSFUL.getCode(), StudentRtnCode.SUCCESSFUL.getMessage(), studentId, null, false);
         
    }

    @Override
    public StudentFullRes CheckAllInfo(String studentId, String targetStudentId) {
//      ?
        return null;
    }

    @Override
    public StudentBasicRes LoginCheck() {
        return null;
    }

}
