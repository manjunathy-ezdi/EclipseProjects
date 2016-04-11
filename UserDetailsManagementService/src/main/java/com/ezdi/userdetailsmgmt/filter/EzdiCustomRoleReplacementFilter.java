package com.ezdi.userdetailsmgmt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.ezdi.userdetailsmgmt.filter.rolehandler.RoleHandler;

public class EzdiCustomRoleReplacementFilter extends GenericFilterBean {

	private final static Logger LOGGER = Logger.getLogger(EzdiCustomRoleReplacementFilter.class);
	private boolean isRoleReplaced = false;
	@Autowired
	private RoleHandler roleHandler;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(!isRoleReplaced){
			replaceRoles((HttpServletRequest)request);
			isRoleReplaced = true;
		}
		chain.doFilter(request, response);
		if(isRoleReplaced){
			replaceRolesBack((HttpServletRequest)request);
			isRoleReplaced = false;
		}
		
	}
	
	
	private void replaceRolesBack(HttpServletRequest request){
		HttpSession session = request.getSession();
		//SecurityContext sc = (SecurityContext)session.getAttribute(REDIS_SPRING_SESSION_SECURITY_KEY);
		//roleHandler.replaceWithOldRoles(sc, session);
		//session.setAttribute(REDIS_SPRING_SESSION_SECURITY_KEY, sc);
		
		roleHandler.replaceWithOldRoles(session);
	}
	
	private void replaceRoles(HttpServletRequest req){
		HttpSession session = req.getSession();
		//SecurityContext sc = (SecurityContext)session.getAttribute(REDIS_SPRING_SESSION_SECURITY_KEY);
		//roleHandler.replaceWithNewRoles(sc, session);
		roleHandler.replaceWithNewRoles(session);
	}
	
	

}
