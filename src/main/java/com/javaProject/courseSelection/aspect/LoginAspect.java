package com.javaProject.courseSelection.aspect;

import com.javaProject.courseSelection.constants.EmployeeRtnCode;
import com.javaProject.courseSelection.vo.EmployeeBasicRes;
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
public class LoginAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution (* com.javaProject.courseSelection.controller.*.*(..)) && "
            + " !execution (* com.javaProject.courseSelection.controller.*.Login(..)) && "
            + " !execution (* com.javaProject.courseSelection.controller.*.ForgetPassword(..)) && "
            + " !execution (* com.javaProject.courseSelection.controller.*.ResetPassword(..)) ")
    public void pointcut() {
        
    }
    
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("==========    around advice    ==========");
//        request = RequestContextHolder.getRequestAttributes()
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        System.out.println("執行的方法名稱: "+signature.getName());
        logger.info("api: "+signature.getName());
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String account = (String)session.getAttribute("account");
        String pwd = (String)session.getAttribute("password");
        if(!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
            return new EmployeeBasicRes(EmployeeRtnCode.NOT_LOGIN_ERROR.getCode(), EmployeeRtnCode.NOT_LOGIN_ERROR.getMessage(), null, null, 0, false);
        }
        Object result = pjp.proceed();
        
//        Object[] obj = new Object[] {account, pwd};
//        Object result = pjp.proceed(obj);
        
        return result;
        
    }
    
}
