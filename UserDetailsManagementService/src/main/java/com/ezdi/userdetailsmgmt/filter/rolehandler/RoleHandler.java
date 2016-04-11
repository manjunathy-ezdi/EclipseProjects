package com.ezdi.userdetailsmgmt.filter.rolehandler;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;

public interface RoleHandler {
	
	public static String ROLE_ATTRIBUTE_PREFIX = "ROLES_";
	
	public void replaceWithNewRoles(SecurityContext sc, HttpSession session);
	
	public void replaceWithOldRoles(SecurityContext sc, HttpSession session);

}
