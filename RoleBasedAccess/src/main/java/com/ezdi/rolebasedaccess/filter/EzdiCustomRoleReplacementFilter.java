package com.ezdi.rolebasedaccess.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.ezdi.rolebasedaccess.rolehandler.RoleHandler;

@Component
public class EzdiCustomRoleReplacementFilter extends GenericFilterBean {

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
		roleHandler.replaceWithOldRoles(session);
	}
	
	private void replaceRoles(HttpServletRequest req){
		HttpSession session = req.getSession();
		roleHandler.replaceWithNewRoles(session);
	}
	
	

}
