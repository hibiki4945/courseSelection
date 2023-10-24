package com.javaProject.courseSelection.service.impl;

import com.javaProject.courseSelection.constants.EmployeeRtnCode;
import com.javaProject.courseSelection.entity.Employee;
import com.javaProject.courseSelection.repository.EmployeeDao;
import com.javaProject.courseSelection.service.ifs.EmployeeService;
import com.javaProject.courseSelection.vo.EmployeeBasicRes;
import com.javaProject.courseSelection.vo.EmployeeChangePasswordReq;
import com.javaProject.courseSelection.vo.EmployeeChangeReq;
import com.javaProject.courseSelection.vo.EmployeeCreateReq;
import com.javaProject.courseSelection.vo.EmployeeDeleteReq;
import com.javaProject.courseSelection.vo.EmployeeFullRes;
import com.javaProject.courseSelection.vo.EmployeeSearchPageRes;
import com.javaProject.courseSelection.vo.EmployeeSearchReq;
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
class EmployeeServiceImpl implements EmployeeService{
//  log
    private Logger logger = LoggerFactory.getLogger(getClass());  
    
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//    private String forgetPasswordEmployeeId; 
    private String forgetPasswordToken; 
    
    @Autowired
    private EmployeeDao eDao;

    @Autowired
    private JavaMailSender mailSender;
    
    private String employeeIdPattern = "^.{2,10}$";
    private String namePattern = "^.{2,30}$";
    private String pwdPattern = "^.{8,30}$";
    private String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    
    @Cacheable(cacheNames = "employee_addInfo", key = "#EmployeeId", unless = "#result.code != '200'")
    @Override
    public EmployeeBasicRes Login(String EmployeeId, String Password) {
        System.out.println("Login!");
        Optional<Employee> res0 = eDao.findById(EmployeeId);
        if(!res0.isPresent()) {
            System.out.println("!res0.isPresent()");
//          記得返回 錯誤訊息
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_NOT_EXIST_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_NOT_EXIST_ERROR.getMessage(), null, null, 0, false);
        }
        Employee res = res0.get();
        
        if(!encoder.matches(Password, res.getPassword())) {
//          記得返回 錯誤訊息
            return new EmployeeBasicRes(EmployeeRtnCode.PASSWORD_ERROR.getCode(), EmployeeRtnCode.PASSWORD_ERROR.getMessage(), null, null, 0, false);
        }
        
        System.out.println("login successed!");
        
        return new EmployeeBasicRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), res.getEmployeeId(), res.getName(), res.getAuthorizationRank(), res.isActivation());
    }

    @Override
    public EmployeeBasicRes Logout(HttpSession httpSession) {
        
        httpSession.invalidate();
        
        return new EmployeeBasicRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), null, null, 0, false);
        
    }

    @Override
    public EmployeeBasicRes ChangePassword(EmployeeChangePasswordReq eChangePasswordReq) {

//      變數的本地化
        String employeeId = eChangePasswordReq.getEmployeeId();
        String oldPassword = eChangePasswordReq.getOldPassword();
        String newPassword = eChangePasswordReq.getNewPassword();
        String newPasswordCheck = eChangePasswordReq.getNewPasswordCheck();

        if(!StringUtils.hasText(employeeId)) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_EMPTY_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_EMPTY_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(!employeeId.matches(employeeIdPattern)) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_FORMAT_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_FORMAT_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(!StringUtils.hasText(oldPassword)) {
            return new EmployeeBasicRes(EmployeeRtnCode.OLD_PASSWORD_EMPTY_ERROR.getCode(), EmployeeRtnCode.OLD_PASSWORD_EMPTY_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(!oldPassword.matches(pwdPattern)) {
            return new EmployeeBasicRes(EmployeeRtnCode.OLD_PASSWORD_FORMAT_ERROR.getCode(), EmployeeRtnCode.OLD_PASSWORD_FORMAT_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(!StringUtils.hasText(newPassword)) {
            return new EmployeeBasicRes(EmployeeRtnCode.NEW_PASSWORD_EMPTY_ERROR.getCode(), EmployeeRtnCode.NEW_PASSWORD_EMPTY_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(!newPassword.matches(pwdPattern)) {
            return new EmployeeBasicRes(EmployeeRtnCode.NEW_PASSWORD_FORMAT_ERROR.getCode(), EmployeeRtnCode.NEW_PASSWORD_FORMAT_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(newPassword.matches(oldPassword)) {
            return new EmployeeBasicRes(EmployeeRtnCode.NEW_PASSWORD_NOT_CHANGE_ERROR.getCode(), EmployeeRtnCode.NEW_PASSWORD_NOT_CHANGE_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(!StringUtils.hasText(newPasswordCheck)) {
            return new EmployeeBasicRes(EmployeeRtnCode.NEW_PASSWORD_CHECK_EMPTY_ERROR.getCode(), EmployeeRtnCode.NEW_PASSWORD_CHECK_EMPTY_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(!newPasswordCheck.matches(pwdPattern)) {
            return new EmployeeBasicRes(EmployeeRtnCode.NEW_PASSWORD_CHECK_FORMAT_ERROR.getCode(), EmployeeRtnCode.NEW_PASSWORD_CHECK_FORMAT_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(!newPassword.matches(newPasswordCheck)) {
            return new EmployeeBasicRes(EmployeeRtnCode.NEW_PASSWORD_CHECK_NOT_EQUAL_ERROR.getCode(), EmployeeRtnCode.NEW_PASSWORD_CHECK_NOT_EQUAL_ERROR.getMessage(), employeeId, null, 0, false);
        }
        Employee res0 = eDao.findById(employeeId).get();
        if(res0 == null) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_NOT_EXIST_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_NOT_EXIST_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(encoder.encode(oldPassword).matches(res0.getPassword())) {
            return new EmployeeBasicRes(EmployeeRtnCode.PASSWORD_ERROR.getCode(), EmployeeRtnCode.PASSWORD_ERROR.getMessage(), employeeId, null, 0, false);
        }

        res0.setPassword(encoder.encode(newPassword));
        Employee res = eDao.save(res0);
        if(res == null) {
            return new EmployeeBasicRes(EmployeeRtnCode.DAO_ERROR.getCode(), EmployeeRtnCode.DAO_ERROR.getMessage(), employeeId, null, 0, false);
        }
        
        if(!res0.isActivation()) {
            Inactive(res0.getEmployeeId());
        } 
        
        return new EmployeeBasicRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), employeeId, null, 0, false);
         
    }

    @Override
    public EmployeeBasicRes Inactive(String employeeId) {

        Employee res0 = eDao.findById(employeeId).get();
        if(!res0.isActivation()) {
            res0.setActivation(true);
        }
        Employee res = eDao.save(res0);
        if(res == null) {
            return new EmployeeBasicRes(EmployeeRtnCode.DAO_ERROR.getCode(), EmployeeRtnCode.DAO_ERROR.getMessage(), employeeId, null, 0, false);
        }
        
        return new EmployeeBasicRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), employeeId, null, 0, false);
        
    }

    @Override
    public EmployeeBasicRes ForgetPassword(String employeeId) {
        
        if(!StringUtils.hasText(employeeId)) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_EMPTY_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_EMPTY_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(!employeeId.matches(employeeIdPattern)) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_FORMAT_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_FORMAT_ERROR.getMessage(), employeeId, null, 0, false);
        }
        Employee res = eDao.findById(employeeId).get();
        if(res == null) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_NOT_EXIST_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_NOT_EXIST_ERROR.getMessage(), employeeId, null, 0, false);
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
        
        return new EmployeeBasicRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), employeeId, null, 0, false);
        
    }


    @Override
    public EmployeeBasicRes CheckToken(String token) {

        if(!StringUtils.hasText(token)) {
            return new EmployeeBasicRes(EmployeeRtnCode.TOKEN_ERROR.getCode(), EmployeeRtnCode.TOKEN_ERROR.getMessage(), null, null, 0, false);
        }
        if(!token.matches(forgetPasswordToken)) {
            return new EmployeeBasicRes(EmployeeRtnCode.TOKEN_ERROR.getCode(), EmployeeRtnCode.TOKEN_ERROR.getMessage(), null, null, 0, false);
        }
        
        return new EmployeeBasicRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), null, null, 0, false);
        
    }
    
    @Override
    public EmployeeBasicRes ResetPassword(String employeeId0, String inputToken0, String newPassword0, String newPasswordCheck0) {
        
//      變數的本地化
        String employeeId = employeeId0;
        String inputToken = inputToken0;
        String newPassword = newPassword0;
        String newPasswordCheck = newPasswordCheck0;

        if(!StringUtils.hasText(employeeId)) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_EMPTY_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_EMPTY_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(!employeeId.matches(employeeIdPattern)) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_FORMAT_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_FORMAT_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(!inputToken.matches(forgetPasswordToken)) {
            return new EmployeeBasicRes(EmployeeRtnCode.TOKEN_ERROR.getCode(), EmployeeRtnCode.TOKEN_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(!newPassword.matches(pwdPattern)) {
            return new EmployeeBasicRes(EmployeeRtnCode.NEW_PASSWORD_FORMAT_ERROR.getCode(), EmployeeRtnCode.NEW_PASSWORD_FORMAT_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(!newPasswordCheck.matches(pwdPattern)) {
            return new EmployeeBasicRes(EmployeeRtnCode.NEW_PASSWORD_CHECK_FORMAT_ERROR.getCode(), EmployeeRtnCode.NEW_PASSWORD_CHECK_FORMAT_ERROR.getMessage(), employeeId, null, 0, false);
        }
        if(!newPassword.matches(newPasswordCheck)) {
            return new EmployeeBasicRes(EmployeeRtnCode.NEW_PASSWORD_CHECK_NOT_EQUAL_ERROR.getCode(), EmployeeRtnCode.NEW_PASSWORD_CHECK_NOT_EQUAL_ERROR.getMessage(), employeeId, null, 0, false);
        }
        Employee res0 = eDao.findById(employeeId).get();
        if(res0 == null) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_NOT_EXIST_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_NOT_EXIST_ERROR.getMessage(), employeeId, null, 0, false);
        }

        res0.setPassword(encoder.encode(newPassword));
        Employee res = eDao.save(res0);
        if(res == null) {
            return new EmployeeBasicRes(EmployeeRtnCode.DAO_ERROR.getCode(), EmployeeRtnCode.DAO_ERROR.getMessage(), employeeId, null, 0, false);
        } 

        forgetPasswordToken = RandomString.make(20);
        
        return new EmployeeBasicRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), employeeId, null, 0, false);
         
    }
    @Override
    public EmployeeBasicRes LoginCheck() {
        return null;
    }
    
/////////////////////////////////////////////////
    @Override
    public EmployeeBasicRes Create(EmployeeCreateReq createReq0) {
        
        logger.info("Employee's Create Start!");
        
//      變數的本地化
        EmployeeCreateReq createReq = null;
        createReq = createReq0;

        if(!StringUtils.hasText(createReq.getEmployeeId())) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_EMPTY_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_EMPTY_ERROR.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
        }
        if(!createReq.getEmployeeId().matches(employeeIdPattern)) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_FORMAT_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_FORMAT_ERROR.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
        }
        if(!StringUtils.hasText(createReq.getName())) {
            return new EmployeeBasicRes(EmployeeRtnCode.NAME_EMPTY_ERROR.getCode(), EmployeeRtnCode.NAME_EMPTY_ERROR.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
        }
        if(!createReq.getName().matches(namePattern)) {
            return new EmployeeBasicRes(EmployeeRtnCode.NAME_FORMAT_ERROR.getCode(), EmployeeRtnCode.NAME_FORMAT_ERROR.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
        }
        if(!StringUtils.hasText(createReq.getEmail())) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMAIL_EMPTY_ERROR.getCode(), EmployeeRtnCode.EMAIL_EMPTY_ERROR.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
        }
        if(createReq.getEmail().length() > 40) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMAIL_OVER_LENGTH_ERROR.getCode(), EmployeeRtnCode.EMAIL_OVER_LENGTH_ERROR.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
        }
        if(!createReq.getEmail().matches(emailPattern)) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMAIL_FORMAT_ERROR.getCode(), EmployeeRtnCode.EMAIL_FORMAT_ERROR.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
        }
        if(createReq.getAuthorizationRank() < 1) {
            return new EmployeeBasicRes(EmployeeRtnCode.AUTHORIZATION_RANK_LESS_THEN_1_ERROR.getCode(), EmployeeRtnCode.AUTHORIZATION_RANK_LESS_THEN_1_ERROR.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
        }
//        if(createReq.getBirthday()) {
//            
//        }
        if(eDao.existsById(createReq.getEmployeeId())) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_EXIST_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_EXIST_ERROR.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
        }
        if(eDao.existsByEmail(createReq.getEmail())) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMAIL_EXIST_ERROR.getCode(), EmployeeRtnCode.EMAIL_EXIST_ERROR.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
        }
        
//        String randomPwd = RandomString.make(10);
        
        Employee employee = new Employee();
        employee.setEmployeeId(createReq.getEmployeeId());
        employee.setName(createReq.getName());
        employee.setPassword(encoder.encode(createReq.getBirthday().toString().replace("-", "")));
        employee.setAuthorizationRank(createReq.getAuthorizationRank());
        employee.setEmail(createReq.getEmail());
        employee.setBirthday(createReq.getBirthday());
        employee.setRegistrationTime(LocalDateTime.now());
        
        Employee res = eDao.save(employee);
        if(res == null) {
            return new EmployeeBasicRes(EmployeeRtnCode.DAO_ERROR.getCode(), EmployeeRtnCode.DAO_ERROR.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
        }
        
        logger.info("Employee's Create Done!");
//        return new EmployeeFullRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), createReq.getEmployeeId(), createReq.getName(), createReq.getAuthorizationRank(), createReq.getEmail(), createReq.getBirthday(), employee.getRegistrationTime(), false);
        return new EmployeeBasicRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
        
    }
    
    @Override
    public EmployeeFullRes Change(EmployeeChangeReq changeReq0) {

        logger.info("Employee's Change Start!");
        
//      變數的本地化
        EmployeeChangeReq changeReq = null;
        changeReq = changeReq0;

        if(!StringUtils.hasText(changeReq.getEmployeeId())) {
            return new EmployeeFullRes(EmployeeRtnCode.EMPLOYEE_ID_EMPTY_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_EMPTY_ERROR.getMessage(), changeReq.getEmployeeId(), changeReq.getName(), 0, null, null, null, false);
        }
        if(!changeReq.getEmployeeId().matches(employeeIdPattern)) {
            return new EmployeeFullRes(EmployeeRtnCode.EMPLOYEE_ID_FORMAT_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_FORMAT_ERROR.getMessage(), changeReq.getEmployeeId(), changeReq.getName(), 0, null, null, null, false);
        }
        if(!StringUtils.hasText(changeReq.getName())) {
            return new EmployeeFullRes(EmployeeRtnCode.NAME_EMPTY_ERROR.getCode(), EmployeeRtnCode.NAME_EMPTY_ERROR.getMessage(), changeReq.getEmployeeId(), changeReq.getName(), 0, null, null, null, false);
        }
        if(!changeReq.getName().matches(namePattern)) {
            return new EmployeeFullRes(EmployeeRtnCode.NAME_FORMAT_ERROR.getCode(), EmployeeRtnCode.NAME_FORMAT_ERROR.getMessage(), changeReq.getEmployeeId(), changeReq.getName(), 0, null, null, null, false);
        }
        if(!StringUtils.hasText(changeReq.getEmail())) {
            return new EmployeeFullRes(EmployeeRtnCode.EMAIL_EMPTY_ERROR.getCode(), EmployeeRtnCode.EMAIL_EMPTY_ERROR.getMessage(), changeReq.getEmployeeId(), changeReq.getName(), 0, null, null, null, false);
        }
        if(changeReq.getEmail().length() > 40) {
            return new EmployeeFullRes(EmployeeRtnCode.EMAIL_OVER_LENGTH_ERROR.getCode(), EmployeeRtnCode.EMAIL_OVER_LENGTH_ERROR.getMessage(), changeReq.getEmployeeId(), changeReq.getName(), 0, null, null, null, false);
        }
        if(!changeReq.getEmail().matches(emailPattern)) {
            return new EmployeeFullRes(EmployeeRtnCode.EMAIL_FORMAT_ERROR.getCode(), EmployeeRtnCode.EMAIL_FORMAT_ERROR.getMessage(), changeReq.getEmployeeId(), changeReq.getName(), 0, null, null, null, false);
        }
        if(changeReq.getAuthorizationRank() < 1) {
            return new EmployeeFullRes(EmployeeRtnCode.AUTHORIZATION_RANK_LESS_THEN_1_ERROR.getCode(), EmployeeRtnCode.AUTHORIZATION_RANK_LESS_THEN_1_ERROR.getMessage(), changeReq.getEmployeeId(), changeReq.getName(), 0, null, null, null, false);
        }
//        if(createReq.getBirthday()) {
//            
//        }
        if(!eDao.existsById(changeReq.getEmployeeId())) {
            return new EmployeeFullRes(EmployeeRtnCode.EMPLOYEE_ID_NOT_EXIST_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_NOT_EXIST_ERROR.getMessage(), changeReq.getEmployeeId(), changeReq.getName(), 0, null, null, null, false);
        }
        List<Employee> res = eDao.findByEmail(changeReq.getEmail());
        LocalDateTime registrationTime = null;
        boolean activation = false;
        if(!res.isEmpty()) {
            for (Employee item : res) {
                registrationTime = item.getRegistrationTime();
                activation = item.isActivation();
                if(!item.getEmployeeId().matches(changeReq.getEmployeeId())) {
                    return new EmployeeFullRes(EmployeeRtnCode.EMAIL_EXIST_ERROR.getCode(), EmployeeRtnCode.EMAIL_EXIST_ERROR.getMessage(), changeReq.getEmployeeId(), changeReq.getName(), 0, null, null, null, false);
                }   
            }
        }
        
//        String randomPwd = RandomString.make(10);
        
        Employee employee = new Employee();
        employee.setEmployeeId(changeReq.getEmployeeId());
        employee.setName(changeReq.getName());
//        employee.setPassword(encoder.encode(changeReq.getBirthday().toString().replace("-", "")));
        employee.setAuthorizationRank(changeReq.getAuthorizationRank());
        employee.setEmail(changeReq.getEmail());
        employee.setBirthday(changeReq.getBirthday());
//        employee.setRegistrationTime(LocalDateTime.now());
        
        int res02 = eDao.change(changeReq.getEmployeeId(), changeReq.getName(), changeReq.getAuthorizationRank(), changeReq.getEmail(), changeReq.getBirthday());
        if(res02 != 1) {
            return new EmployeeFullRes(EmployeeRtnCode.DAO_ERROR.getCode(), EmployeeRtnCode.DAO_ERROR.getMessage(), changeReq.getEmployeeId(), changeReq.getName(), 0, null, null, null, false);
        }

        logger.info("Employee's Change Done!");
//        return new EmployeeFullRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), createReq.getEmployeeId(), createReq.getName(), createReq.getAuthorizationRank(), createReq.getEmail(), createReq.getBirthday(), employee.getRegistrationTime(), false);
        return new EmployeeFullRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), changeReq.getEmployeeId(), changeReq.getName(), changeReq.getAuthorizationRank(), changeReq.getEmail(), changeReq.getBirthday(), registrationTime, activation);
         
    }

    @Override
    public EmployeeSearchPageRes Search(EmployeeSearchReq searchReq0) {

        logger.info("Employee's Search Start!");
        
//      變數的本地化
        EmployeeSearchReq searchReq = null;
        searchReq = searchReq0;

//        if(!StringUtils.hasText(searchReq.getEmployeeId())) {
//            return new EmployeeFullRes(EmployeeRtnCode.EMPLOYEE_ID_EMPTY_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_EMPTY_ERROR.getMessage(), searchReq.getEmployeeId(), searchReq.getName(), 0, null, null, null, false);
//        }
//        if(!searchReq.getEmployeeId().matches(employeeIdPattern)) {
//            return new EmployeeFullRes(EmployeeRtnCode.EMPLOYEE_ID_FORMAT_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_FORMAT_ERROR.getMessage(), searchReq.getEmployeeId(), searchReq.getName(), 0, null, null, null, false);
//        }
//        if(!StringUtils.hasText(searchReq.getName())) {
//            return new EmployeeFullRes(EmployeeRtnCode.NAME_EMPTY_ERROR.getCode(), EmployeeRtnCode.NAME_EMPTY_ERROR.getMessage(), searchReq.getEmployeeId(), searchReq.getName(), 0, null, null, null, false);
//        }
//        if(!searchReq.getName().matches(namePattern)) {
//            return new EmployeeFullRes(EmployeeRtnCode.NAME_FORMAT_ERROR.getCode(), EmployeeRtnCode.NAME_FORMAT_ERROR.getMessage(), searchReq.getEmployeeId(), searchReq.getName(), 0, null, null, null, false);
//        }
//        if(!StringUtils.hasText(searchReq.getEmail())) {
//            return new EmployeeFullRes(EmployeeRtnCode.EMAIL_EMPTY_ERROR.getCode(), EmployeeRtnCode.EMAIL_EMPTY_ERROR.getMessage(), searchReq.getEmployeeId(), searchReq.getName(), 0, null, null, null, false);
//        }
//        if(searchReq.getEmail().length() > 40) {
//            return new EmployeeFullRes(EmployeeRtnCode.EMAIL_OVER_LENGTH_ERROR.getCode(), EmployeeRtnCode.EMAIL_OVER_LENGTH_ERROR.getMessage(), searchReq.getEmployeeId(), searchReq.getName(), 0, null, null, null, false);
//        }
//        if(!searchReq.getEmail().matches(emailPattern)) {
//            return new EmployeeFullRes(EmployeeRtnCode.EMAIL_FORMAT_ERROR.getCode(), EmployeeRtnCode.EMAIL_FORMAT_ERROR.getMessage(), searchReq.getEmployeeId(), searchReq.getName(), 0, null, null, null, false);
//        }
//        if(searchReq.getAuthorizationRank() < 1) {
//            return new EmployeeFullRes(EmployeeRtnCode.AUTHORIZATION_RANK_LESS_THEN_1_ERROR.getCode(), EmployeeRtnCode.AUTHORIZATION_RANK_LESS_THEN_1_ERROR.getMessage(), searchReq.getEmployeeId(), searchReq.getName(), 0, null, null, null, false);
//        }
//        if(searchReq.getBirthdayEnd().isBefore(searchReq.getBirthdayStart())) {
//            return new EmployeeFullRes(EmployeeRtnCode.BIRTHDAY_RANGE_ERROR.getCode(), EmployeeRtnCode.BIRTHDAY_RANGE_ERROR.getMessage(), searchReq.getEmployeeId(), searchReq.getName(), 0, null, null, null, false);
//        }
//        if(!searchReq.getBirthdayEnd().isBefore(LocalDate.now())) {
//            return new EmployeeFullRes(EmployeeRtnCode.BIRTHDAY_NOT_YET_ERROR.getCode(), EmployeeRtnCode.BIRTHDAY_NOT_YET_ERROR.getMessage(), searchReq.getEmployeeId(), searchReq.getName(), 0, null, null, null, false);
//        }
//        if(searchReq.getRegistrationTimeEnd().isBefore(searchReq.getRegistrationTimeStart())) {
//            return new EmployeeFullRes(EmployeeRtnCode.REGISTRATION_RANGE_ERROR.getCode(), EmployeeRtnCode.REGISTRATION_RANGE_ERROR.getMessage(), searchReq.getEmployeeId(), searchReq.getName(), 0, null, null, null, false);
//        }
//        if(!searchReq.getRegistrationTimeEnd().isBefore(LocalDateTime.now())) {
//            return new EmployeeFullRes(EmployeeRtnCode.REGISTRATION_NOT_YET_ERROR.getCode(), EmployeeRtnCode.REGISTRATION_NOT_YET_ERROR.getMessage(), searchReq.getEmployeeId(), searchReq.getName(), 0, null, null, null, false);
//        }
//      宣告 排序用變數 
        Sort sort = null;
        
//        sort = Sort.by(Sort.Direction.DESC , "employee_id");
        sort = Sort.by(Sort.Direction.DESC , "employee_id");
        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<Employee> res = eDao.search(pageable, searchReq.getEmployeeId(), searchReq.getName(), searchReq.getAuthorizationRank(), searchReq.getEmail(), searchReq.getBirthdayStart(), searchReq.getBirthdayEnd(), searchReq.getRegistrationTimeStart(), searchReq.getRegistrationTimeEnd(), searchReq.getActivation());

        logger.info("Employee's Search End!");
        
        return new EmployeeSearchPageRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), res);
        
    }

    @Override
    public EmployeeBasicRes Delete(EmployeeDeleteReq deleteReq0) {
        
        logger.info("Employee's Delete Start!");
        
//      變數的本地化
        EmployeeDeleteReq deleteReq = null;
        deleteReq = deleteReq0;
        
        if(!eDao.existsById(deleteReq.getEmployeeId())) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_NOT_EXIST_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_NOT_EXIST_ERROR.getMessage(), deleteReq.getEmployeeId(), null, 0, false);
        }
//        ?
        if(eDao.removeByEmployeeId(deleteReq.getEmployeeId()) != 1) {
            return new EmployeeBasicRes(EmployeeRtnCode.DAO_ERROR.getCode(), EmployeeRtnCode.DAO_ERROR.getMessage(), deleteReq.getEmployeeId(), null, 0, false);
        }

        logger.info("Employee's Delete End!");
        
        return new EmployeeBasicRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), deleteReq.getEmployeeId(), null, 0, false);
        
    }
        
}
