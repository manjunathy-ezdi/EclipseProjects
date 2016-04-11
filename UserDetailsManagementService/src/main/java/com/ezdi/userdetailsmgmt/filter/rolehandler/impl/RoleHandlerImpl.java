package com.ezdi.userdetailsmgmt.filter.rolehandler.impl;

import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;

import com.ezdi.userdetailsmgmt.UserDetailsMgmtApplication;
import com.ezdi.userdetailsmgmt.filter.rolehandler.RoleHandler;
import com.ezdi.userdetailsmgmt.persistence.Role;
import com.ezdi.userdetailsmgmt.repository.RoleRepository;


public abstract class RoleHandlerImpl implements RoleHandler {
	
	
	public RoleHandlerImpl() {
		init();
	}
	
	public void init(){
		//newRoles = new HashSet<GrantedAuthority>();
	}
	
	private SecurityContext originalSecurityContext;
	
	private SecurityContext newSecurityContext;
	
	public String getRoleAttribute(){
		return ROLE_ATTRIBUTE_PREFIX+UserDetailsMgmtApplication.MICROSERVICE_ID;
	}
	
	/*
	 * SETTING NEW ROLES
	 */
	
	private Collection<GrantedAuthority> getAuthoritiesBySessionAttribute(HttpSession session){
		if(session != null){
			return (Collection<GrantedAuthority>) session.getAttribute(getRoleAttribute());
		}
		return null;
	}
	
	protected SecurityContext createNewSecurityContext(Authentication auth){
		SecurityContextImpl ret = new SecurityContextImpl();
		ret.setAuthentication(auth);
		return ret;
	}
	
	protected SecurityContext getSecurityContext(){
		return SecurityContextHolder.getContext();
	}
	
	protected void setSecurityContext(SecurityContext sc){
		SecurityContextHolder.setContext(sc);
	}
	
	@Override
	public void replaceWithNewRoles(HttpSession session){
		originalSecurityContext = getSecurityContext();
		if(originalSecurityContext != null && session != null){
			Collection<GrantedAuthority> newRoles = getAuthoritiesBySessionAttribute(session);
			if(newRoles == null || newRoles.isEmpty()){
				newRoles = getAuthoritiesByRepository(session);
			}
			createAndSetNewSecurityContext(newRoles);
		}
	}
	
	private boolean createAndSetNewSecurityContext(Collection<GrantedAuthority> newRoles){
		if(originalSecurityContext != null){
			Authentication originalAuth = originalSecurityContext.getAuthentication();
			if(originalAuth != null){
				Authentication newAuth = new UsernamePasswordAuthenticationToken(originalAuth.getPrincipal(), originalAuth.getCredentials(), newRoles);
				newSecurityContext = createNewSecurityContext(newAuth);
				setSecurityContext(newSecurityContext);
				return true;
			}
		}
		return false;
	}
	
	private Collection<GrantedAuthority> getAuthoritiesByRepository(HttpSession session){
		Authentication originalAuth;
		Collection<GrantedAuthority> newRoles = new HashSet<GrantedAuthority>();
		if(session != null && originalSecurityContext != null && (originalAuth = originalSecurityContext.getAuthentication())!=null){
			Collection<? extends GrantedAuthority> originalRoles = originalAuth.getAuthorities();
			if(originalRoles != null && !originalRoles.isEmpty()){
				for(GrantedAuthority each: originalRoles){
					newRoles.addAll(setOfNewAuthorities(each.getAuthority()));
				}
			}
		}
		return newRoles;
	}
	

	protected Collection<SimpleGrantedAuthority> setOfNewAuthorities(String roleName) {
		Collection<String> roleList = getRoles(roleName);
		Collection<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
		if(roleList != null){
			for(String each: roleList){
				authorities.add(new SimpleGrantedAuthority(each));
			}
		}
		return authorities;
	}
	
	protected abstract Collection<String> getRoles(String roleName);
	
	
	/*
	 * SETTING BACK OLD ROLES
	 */
	
	private void updateMicroserviceRoles(HttpSession session){
		Authentication newAuth;
		if(newSecurityContext != null && session != null && (newAuth=newSecurityContext.getAuthentication())!=null){
			session.setAttribute(getRoleAttribute(), newAuth.getAuthorities());
		}
	}
	
	
	
	private void revertRoles(){
		SecurityContext sc = getSecurityContext();
		Authentication auth = sc.getAuthentication();
		if(originalSecurityContext.getAuthentication() != null){
			if(auth != null){
				UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), originalSecurityContext.getAuthentication().getAuthorities());
				newAuth.setDetails(auth.getDetails());
				sc.setAuthentication(newAuth);
			}
		}
	}
	
	
	@Override
	public void replaceWithOldRoles(HttpSession session){
		updateMicroserviceRoles(session);
		revertRoles();
		session.setAttribute(RoleHandler.REDIS_SPRING_SESSION_SECURITY_KEY, getSecurityContext());
	}
}
