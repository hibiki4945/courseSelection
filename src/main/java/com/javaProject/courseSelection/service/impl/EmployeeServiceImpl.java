package com.javaProject.courseSelection.service.impl;

import com.javaProject.courseSelection.constants.EmployeeRtnCode;
import com.javaProject.courseSelection.entity.Employee;
import com.javaProject.courseSelection.repository.EmployeeDao;
import com.javaProject.courseSelection.service.ifs.EmployeeService;
import com.javaProject.courseSelection.vo.EmployeeBasicRes;
import com.javaProject.courseSelection.vo.EmployeeCreateReq;
import com.javaProject.courseSelection.vo.EmployeeFullRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
class EmployeeServiceImpl implements EmployeeService{
//  log
    private Logger logger = LoggerFactory.getLogger(getClass());  
    
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    @Autowired
    private EmployeeDao eDao;

    private String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private String pwdPattern = "^.{8,30}$";
    private String namePattern = "^.{2,30}$";
    
    @Override
    public EmployeeBasicRes Create(EmployeeCreateReq createReq0) {
        
        logger.info("Employee's Create Start!");
        
//      變數的本地化
        EmployeeCreateReq createReq = null;
        createReq = createReq0;

        if(!StringUtils.hasText(createReq.getEmployeeId())) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_EMPTY_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_EMPTY_ERROR.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
        }
        if(createReq.getEmployeeId().length() > 10) {
            return new EmployeeBasicRes(EmployeeRtnCode.EMPLOYEE_ID_OVER_LENGTH_ERROR.getCode(), EmployeeRtnCode.EMPLOYEE_ID_OVER_LENGTH_ERROR.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
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
        
        eDao.save(employee);

        logger.info("Employee's Create Done!");
//        return new EmployeeFullRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), createReq.getEmployeeId(), createReq.getName(), createReq.getAuthorizationRank(), createReq.getEmail(), createReq.getBirthday(), employee.getRegistrationTime(), false);
        return new EmployeeBasicRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), createReq.getEmployeeId(), createReq.getName(), 0, false);
        
    }
    
//    ?
    @Cacheable(cacheNames = "employee_addInfo", key = "#EmployeeId", unless = "#result.code != '200'")
    @Override
    public EmployeeBasicRes Login(String EmployeeId, String Password) {
        System.out.println("Login!");
        Optional<Employee> res0 = eDao.findById(EmployeeId);
        if(!res0.isPresent()) {
            System.out.println("!res0.isPresent()");
//          記得返回 錯誤訊息
            return new EmployeeBasicRes(EmployeeRtnCode.NOT_LOGIN_ERROR.getCode(), EmployeeRtnCode.NOT_LOGIN_ERROR.getMessage(), null, null, 0, false);
        }
        Employee res = res0.get();
        
        if(!encoder.matches(Password, res.getPassword())) {
//          記得返回 錯誤訊息
            return new EmployeeBasicRes(EmployeeRtnCode.FAILED.getCode(), EmployeeRtnCode.FAILED.getMessage(), null, null, 0, false);
        }
        
        System.out.println("login successed!");
        
        return new EmployeeBasicRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), res.getEmployeeId(), res.getName(), 0, false);
    }

    @Override
    public EmployeeBasicRes Logout(HttpSession httpSession) {
        
        httpSession.invalidate();
        
        return new EmployeeBasicRes(EmployeeRtnCode.SUCCESSFUL.getCode(), EmployeeRtnCode.SUCCESSFUL.getMessage(), null, null, 0, false);
        
    }

    @Override
    public EmployeeBasicRes ChangePassword(String EmployeeId, String OldPassword, String NewPassword, String NewPasswordCheck) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EmployeeBasicRes Inactive(String EmployeeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EmployeeBasicRes ForgetPassword(String EmployeeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EmployeeBasicRes ResetPassword(String EmployeeId, String NewPassword, String NewPasswordCheck) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EmployeeFullRes CheckAllInfo(String EmployeeId, String TargetEmployeeId) {
        // TODO Auto-generated method stub
        return null;
    }

}
