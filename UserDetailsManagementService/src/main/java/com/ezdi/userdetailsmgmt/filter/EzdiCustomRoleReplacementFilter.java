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

	private static String REDIS_SPRING_SESSION_SECURITY_KEY = "SPRING_SECURITY_CONTEXT";

	private final static Logger LOGGER = Logger.getLogger(EzdiCustomRoleReplacementFilter.class);
	private boolean isRoleReplaced = false;
	@Autowired
	private RoleHandler roleHandler;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(!isRoleReplaced){
			processRequest((HttpServletRequest)request);
			isRoleReplaced = true;
		}
		chain.doFilter(request, response);
		if(isRoleReplaced){
			putRolesBack((HttpServletRequest)request);
			isRoleReplaced = false;
		}
		
	}
	
	
	private void putRolesBack(HttpServletRequest request){
		HttpSession session = request.getSession();
		SecurityContext sc = (SecurityContext)session.getAttribute(REDIS_SPRING_SESSION_SECURITY_KEY);
		roleHandler.replaceWithOldRoles(sc, session);
	}
	
	private void debugDetails(HttpServletRequest req){
		LOGGER.info("YAJI :: processRequest()");
		HttpSession session = req.getSession();
		LOGGER.info("YAJI :: processRequest() : session.toString() : "+session.toString());
		LOGGER.info("YAJI :: processRequest() : session.getId : "+session.getId());
		SecurityContext sc = (SecurityContext)session.getAttribute(REDIS_SPRING_SESSION_SECURITY_KEY);
		if(sc != null){
			SecurityContextHolder.getContext().setAuthentication(sc.getAuthentication());
		}
		else{
			LOGGER.error("No Security Context object for "+req.getSession().getId());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth != null){
				LOGGER.info("AUHT:: "+auth.getName());
			}
			else{
				LOGGER.info("Auth object is also null");
			}
		}
	}
	
	private void processRequest(HttpServletRequest req){
		HttpSession session = req.getSession();
		SecurityContext sc = (SecurityContext)session.getAttribute(REDIS_SPRING_SESSION_SECURITY_KEY);
		roleHandler.replaceWithNewRoles(sc, session);
	}
	
	

}
