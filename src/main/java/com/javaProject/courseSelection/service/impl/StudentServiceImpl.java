package com.javaProject.courseSelection.service.impl;

import com.javaProject.courseSelection.constants.StudentRtnCode;
import com.javaProject.courseSelection.entity.Student;
import com.javaProject.courseSelection.repository.StudentDao;
import com.javaProject.courseSelection.service.ifs.StudentService;
import com.javaProject.courseSelection.vo.StudentBasicRes;
import com.javaProject.courseSelection.vo.StudentChangePasswordReq;
import com.javaProject.courseSelection.vo.StudentChangeReq;
import com.javaProject.courseSelection.vo.StudentCreateReq;
import com.javaProject.courseSelection.vo.StudentDeleteReq;
import com.javaProject.courseSelection.vo.StudentFullRes;
import com.javaProject.courseSelection.vo.StudentSearchPageRes;
import com.javaProject.courseSelection.vo.StudentSearchReq;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.List;
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

    @Override
    public StudentFullRes Change(StudentChangeReq changeReq0) {

        logger.info("Student's Change Start!");
        
//      變數的本地化
        StudentChangeReq changeReq = null;
        changeReq = changeReq0;

        if(!StringUtils.hasText(changeReq.getStudentId())) {
            return new StudentFullRes(StudentRtnCode.STUDENT_ID_EMPTY_ERROR.getCode(), StudentRtnCode.STUDENT_ID_EMPTY_ERROR.getMessage(), changeReq.getStudentId(), changeReq.getName(), null, null, null, false, false);
        }
        if(!changeReq.getStudentId().matches(studentIdPattern)) {
            return new StudentFullRes(StudentRtnCode.STUDENT_ID_FORMAT_ERROR.getCode(), StudentRtnCode.STUDENT_ID_FORMAT_ERROR.getMessage(), changeReq.getStudentId(), changeReq.getName(), null, null, null, false, false);
        }
        if(!StringUtils.hasText(changeReq.getName())) {
            return new StudentFullRes(StudentRtnCode.NAME_EMPTY_ERROR.getCode(), StudentRtnCode.NAME_EMPTY_ERROR.getMessage(), changeReq.getStudentId(), changeReq.getName(), null, null, null, false, false);
        }
        if(!changeReq.getName().matches(namePattern)) {
            return new StudentFullRes(StudentRtnCode.NAME_FORMAT_ERROR.getCode(), StudentRtnCode.NAME_FORMAT_ERROR.getMessage(), changeReq.getStudentId(), changeReq.getName(), null, null, null, false, false);
        }
        if(!StringUtils.hasText(changeReq.getEmail())) {
            return new StudentFullRes(StudentRtnCode.EMAIL_EMPTY_ERROR.getCode(), StudentRtnCode.EMAIL_EMPTY_ERROR.getMessage(), changeReq.getStudentId(), changeReq.getName(), null, null, null, false, false);
        }
        if(changeReq.getEmail().length() > 40) {
            return new StudentFullRes(StudentRtnCode.EMAIL_OVER_LENGTH_ERROR.getCode(), StudentRtnCode.EMAIL_OVER_LENGTH_ERROR.getMessage(), changeReq.getStudentId(), changeReq.getName(), null, null, null, false, false);
        }
        if(!changeReq.getEmail().matches(emailPattern)) {
            return new StudentFullRes(StudentRtnCode.EMAIL_FORMAT_ERROR.getCode(), StudentRtnCode.EMAIL_FORMAT_ERROR.getMessage(), changeReq.getStudentId(), changeReq.getName(), null, null, null, false, false);
        }
//        if(createReq.getBirthday()) {
//            
//        }
        if(!sDao.existsById(changeReq.getStudentId())) {
            return new StudentFullRes(StudentRtnCode.STUDENT_ID_NOT_EXIST_ERROR.getCode(), StudentRtnCode.STUDENT_ID_NOT_EXIST_ERROR.getMessage(), changeReq.getStudentId(), changeReq.getName(), null, null, null, false, false);
        }
        List<Student> res = sDao.findByEmail(changeReq.getEmail());
        LocalDateTime registrationTime = null;
        boolean activation = false;
        if(!res.isEmpty()) {
            for (Student item : res) {
                registrationTime = item.getRegistrationTime();
                activation = item.isActivation();
                if(!item.getStudentId().matches(changeReq.getStudentId())) {
                    return new StudentFullRes(StudentRtnCode.EMAIL_EXIST_ERROR.getCode(), StudentRtnCode.EMAIL_EXIST_ERROR.getMessage(), changeReq.getStudentId(), changeReq.getName(), null, null, null, false, false);
                }   
            }
        }
        
//        String randomPwd = RandomString.make(10);
        
        Student student = new Student();
        student.setStudentId(changeReq.getStudentId());
        student.setName(changeReq.getName());
//        employee.setPassword(encoder.encode(changeReq.getBirthday().toString().replace("-", "")));
//        employee.setAuthorizationRank(changeReq.getAuthorizationRank());
        student.setEmail(changeReq.getEmail());
        student.setBirthday(changeReq.getBirthday());
//        employee.setRegistrationTime(LocalDateTime.now());
        
        int res02 = sDao.change(changeReq.getStudentId(), changeReq.getName(), changeReq.getEmail(), changeReq.getBirthday());
        if(res02 != 1) {
            return new StudentFullRes(StudentRtnCode.DAO_ERROR.getCode(), StudentRtnCode.DAO_ERROR.getMessage(), changeReq.getStudentId(), changeReq.getName(), null, null, null, false, false);
        }

        logger.info("Student's Change Done!");
//        return new EmployeeFullRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), createReq.getEmployeeId(), createReq.getName(), createReq.getAuthorizationRank(), createReq.getEmail(), createReq.getBirthday(), employee.getRegistrationTime(), false);
        return new StudentFullRes(StudentRtnCode.SUCCESSFUL.getCode(), StudentRtnCode.SUCCESSFUL.getMessage(), changeReq.getStudentId(), changeReq.getName(), changeReq.getEmail(), changeReq.getBirthday(), registrationTime, activation, false);
         
    }

    @Override
    public StudentSearchPageRes Search(StudentSearchReq searchReq0) {

        logger.info("Student's Search Start!");
        
//      變數的本地化
        StudentSearchReq searchReq = null;
        searchReq = searchReq0;

//      宣告 排序用變數 
        Sort sort = null;
        
//        sort = Sort.by(Sort.Direction.DESC , "employee_id");
        sort = Sort.by(Sort.Direction.DESC , "student_id");
        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<Student> res = sDao.search(pageable, searchReq.getStudentId(), searchReq.getName(), searchReq.getEmail(), searchReq.getBirthdayStart(), searchReq.getBirthdayEnd(), searchReq.getRegistrationTimeStart(), searchReq.getRegistrationTimeEnd(), searchReq.getActivation(), searchReq.getSubsidy());
        
        logger.info("Student's Search End!");
        
        return new StudentSearchPageRes(StudentRtnCode.SUCCESSFUL.getCode(), StudentRtnCode.SUCCESSFUL.getMessage(), res);
        
    }

    @Override
    public StudentBasicRes Delete(StudentDeleteReq deleteReq0) {

        logger.info("Student's Delete Start!");
        
//      變數的本地化
        StudentDeleteReq deleteReq = null;
        deleteReq = deleteReq0;
        
        if(!sDao.existsById(deleteReq.getStudentId())) {
            return new StudentBasicRes(StudentRtnCode.STUDENT_ID_NOT_EXIST_ERROR.getCode(), StudentRtnCode.STUDENT_ID_NOT_EXIST_ERROR.getMessage(), deleteReq.getStudentId(), null, false);
        }
//        ?
        if(sDao.removeByStudentId(deleteReq.getStudentId()) != 1) {
            return new StudentBasicRes(StudentRtnCode.DAO_ERROR.getCode(), StudentRtnCode.DAO_ERROR.getMessage(), deleteReq.getStudentId(), null, false);
        }

        logger.info("Student's Delete End!");
        
        return new StudentBasicRes(StudentRtnCode.SUCCESSFUL.getCode(), StudentRtnCode.SUCCESSFUL.getMessage(), deleteReq.getStudentId(), null, false);
        
    }

}
