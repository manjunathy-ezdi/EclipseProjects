package com.ezdi.springsecurity.securityhandler.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.GenericFilterBean;

public class EzdiSecuritySessionFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
		invalidateSessionIfUnauthorized(request,response);
	}
	
	/*
	 * Required because an extra session is created by Tomcat and is stored in Redis. This is unnecessary. 
	 * Could result in memory overflow in Redis if DoS (Denial of Sevice) attack.
	 */
	private void invalidateSessionIfUnauthorized(ServletRequest request, ServletResponse response){
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		/*
		if(resp.getStatus() == HttpServletResponse.SC_FORBIDDEN){
			req.getSession().invalidate(); 
			// THIS ACTUALLY WORKS.. SESSION WILL NOT BE STORED IN REDIS. 
			// BUT EXTREMELY BAD WAY TO DECIDE WHETHER SESSION HAS TO BE INVALIDATED.
		}
		*/
		
		/* HttpServletRequest req = (HttpServletRequest)request;
		response.getOutputStream().println(req.getSession().getId());
		*/
		
		/*
		response.getWriter().append("Testing");
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader("BeforeSession", "YES");
		if(resp.getStatus() == HttpServletResponse.SC_FORBIDDEN){
			HttpServletRequest req = (HttpServletRequest) request;
			resp.setHeader("DuringSession", req.getSession().getId());
			req.getSession().invalidate();
			resp.setHeader("AfterSession1", "INVALIDATED");
		}
		resp.setHeader("AfterSession2", "OUTTA IF");
		*/
	}
	
}
