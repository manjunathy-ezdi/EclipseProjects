package com.ezdi.rolebasedaccess.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ezdi.rolebasedaccess.rolehandler.RoleHandler;

@Component
@Aspect
@Order(Integer.MAX_VALUE)
public class RoleReplacementAspect {
	
	@Autowired
	private RoleHandler roleHandler;
	
	@Pointcut("within(com.ezdi..*.controller.*)")
    public void allControllerMethodsPointcut() {
    }

    @AfterReturning("allControllerMethodsPointcut()")
    public void replaceWithOldRoles(final JoinPoint joinPoint){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        HttpSession session = request.getSession();
        if (session != null){
        	roleHandler.replaceWithOldRoles(session);
        }
    }

}
