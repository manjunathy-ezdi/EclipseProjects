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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class EzdiCustomRoleReplacementFilter extends GenericFilterBean {

	private static String REDIS_SPRING_SESSION_SECURITY_KEY = "SPRING_SECURITY_CONTEXT";

	private final static Logger LOGGER = Logger.getLogger(EzdiCustomRoleReplacementFilter.class);
	
	//@Autowired
	//AuthenticationManager authManager;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		LOGGER.info("DADADADA");
		
		processRequest((HttpServletRequest)request);
		
		LOGGER.info("DIDIDIIDID");
		
		chain.doFilter(request, response);
		
	}
	
	private void processRequest(HttpServletRequest req){
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
		}
	}
	
	

}
