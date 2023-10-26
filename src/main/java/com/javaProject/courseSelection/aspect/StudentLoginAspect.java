package com.javaProject.courseSelection.aspect;

import com.javaProject.courseSelection.constants.StudentRtnCode;
import com.javaProject.courseSelection.vo.StudentBasicRes;
import com.javaProject.courseSelection.vo.StudentFullRes;
import com.javaProject.courseSelection.vo.StudentSearchPageRes;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Aspect
public class StudentLoginAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution (* com.javaProject.courseSelection.controller.StudentController.*(..)) && "
            + " !execution (* com.javaProject.courseSelection.controller.StudentController.Login(..)) && "
            + " !execution (* com.javaProject.courseSelection.controller.StudentController.ForgetPassword(..)) && "
            + " !execution (* com.javaProject.courseSelection.controller.StudentController.CheckToken(..)) && "
            + " !execution (* com.javaProject.courseSelection.controller.StudentController.ResetPassword(..)) && "
            + " !execution (* com.javaProject.courseSelection.controller.StudentController.Change(..)) && "
            + " !execution (* com.javaProject.courseSelection.controller.StudentController.Search(..)) ")
    public void pointcut() {
        
    }
    
    @Pointcut("execution (* com.javaProject.courseSelection.controller.StudentController.Change(..)) ")
    public void pointcut2() {
        
    }
    
    @Pointcut("execution (* com.javaProject.courseSelection.controller.StudentController.Search(..)) ")
    public void pointcut3() {
        
    }
    
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("==========    around advice    ==========");
//        request = RequestContextHolder.getRequestAttributes()
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        System.out.println("執行的方法名稱: "+signature.getName());
        logger.info("api: "+signature.getName());
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpServletResponse response = 
        HttpSession session = request.getSession();
        String account = (String)session.getAttribute("account");
        String pwd = (String)session.getAttribute("password");
        if(!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
            return new StudentBasicRes(StudentRtnCode.NOT_LOGIN_ERROR.getCode(), StudentRtnCode.NOT_LOGIN_ERROR.getMessage(), null, null, false);
        }
        Object result = pjp.proceed();
        
//        Object[] obj = new Object[] {account, pwd};
//        Object result = pjp.proceed(obj);
        
        return result;
        
    }
    
    @Around("pointcut2()")
    public Object around2(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("==========    around advice    ==========");
//        request = RequestContextHolder.getRequestAttributes()
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        System.out.println("執行的方法名稱: "+signature.getName());
        logger.info("api: "+signature.getName());
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpServletResponse response = 
        HttpSession session = request.getSession();
        String account = (String)session.getAttribute("account");
        String pwd = (String)session.getAttribute("password");
        if(!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
            return new StudentFullRes(StudentRtnCode.NOT_LOGIN_ERROR.getCode(), StudentRtnCode.NOT_LOGIN_ERROR.getMessage(), null, null, null, null, null, false, false);
        }
        Object result = pjp.proceed();
        
//        Object[] obj = new Object[] {account, pwd};
//        Object result = pjp.proceed(obj);
        
        return result;
        
    }
    
    @Around("pointcut3()")
    public Object around3(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("==========    around advice    ==========");
//        request = RequestContextHolder.getRequestAttributes()
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        System.out.println("執行的方法名稱: "+signature.getName());
        logger.info("api: "+signature.getName());
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpServletResponse response = 
        HttpSession session = request.getSession();
        String account = (String)session.getAttribute("account");
        String pwd = (String)session.getAttribute("password");
        if(!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
            return new StudentSearchPageRes(StudentRtnCode.NOT_LOGIN_ERROR.getCode(), StudentRtnCode.NOT_LOGIN_ERROR.getMessage(), null);
        }
        Object result = pjp.proceed();
        
//        Object[] obj = new Object[] {account, pwd};
//        Object result = pjp.proceed(obj);
        
        return result;
        
    }
    
}
