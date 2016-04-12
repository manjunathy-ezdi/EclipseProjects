package com.ezdi.rolebasedaccess.rolehandler;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;

public interface RoleHandler {
	
	public static String ROLE_ATTRIBUTE_PREFIX = "ROLES_";
	public static String REDIS_SPRING_SESSION_SECURITY_KEY = "SPRING_SECURITY_CONTEXT";	
	
	public void replaceWithNewRoles(HttpSession session);
	
	public void replaceWithOldRoles(HttpSession session);

}
