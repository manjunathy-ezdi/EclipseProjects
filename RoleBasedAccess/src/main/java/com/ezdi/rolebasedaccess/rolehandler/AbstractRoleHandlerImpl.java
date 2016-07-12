package com.ezdi.rolebasedaccess.rolehandler;

import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

@PropertySource("application.properties")
public abstract class AbstractRoleHandlerImpl implements RoleHandler {
	
	@Value("${microservice.id}")
	private int microserviceId;
	
	private Authentication originalAuthentication;
	
	private Authentication newAuthentication;
	
	public String getRoleAttribute(){
		if(microserviceId == 0){
			throw new IllegalStateException("Microservice ID has not been defined.");
		}
		return ROLE_ATTRIBUTE_PREFIX+microserviceId;
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
	
	protected Authentication getAuthentication(){
		SecurityContext sc = SecurityContextHolder.getContext();
		if(sc != null){
			Authentication au = sc.getAuthentication();
			if(au!=null){
				Authentication auth = new UsernamePasswordAuthenticationToken(au.getPrincipal(), au.getCredentials(), au.getAuthorities());
				return auth;
			}
		}
		return null;
	}
	
	protected void setAuthentication(Authentication auth){
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	@Override
	public void replaceWithNewRoles(HttpSession session){
		originalAuthentication = getAuthentication();
		if(originalAuthentication != null && session != null){
			Collection<GrantedAuthority> newRoles = getAuthoritiesBySessionAttribute(session);
			if(newRoles == null || newRoles.isEmpty()){
				newRoles = getAuthoritiesByRepository();
			}
			createAndSetNewAuthentication(newRoles);
		}
	}
	
	private void createAndSetNewAuthentication(Collection<GrantedAuthority> newRoles){
		newAuthentication = new UsernamePasswordAuthenticationToken(originalAuthentication.getPrincipal(), originalAuthentication.getCredentials(), newRoles);
		setAuthentication(newAuthentication);
	}
	
	private Collection<GrantedAuthority> getAuthoritiesByRepository(){
		Collection<GrantedAuthority> newRoles = new HashSet<GrantedAuthority>();
		if(originalAuthentication != null){
			Collection<? extends GrantedAuthority> originalRoles = originalAuthentication.getAuthorities();
			if(originalRoles != null && !originalRoles.isEmpty()){
				for(GrantedAuthority each: originalRoles){
					newRoles.addAll(getSetOfNewAuthoritiesForRoleName(each.getAuthority()));
				}
			}
		}
		return newRoles;
	}
	

	protected Collection<SimpleGrantedAuthority> getSetOfNewAuthoritiesForRoleName(String roleName) {
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
		if(newAuthentication != null && session != null){
			session.setAttribute(getRoleAttribute(), newAuthentication.getAuthorities());
		}
	}
		
	
	private void revertRoles(){
		SecurityContextHolder.getContext().setAuthentication(originalAuthentication);
	}
	
	
	@Override
	public void replaceWithOldRoles(HttpSession session){
		updateMicroserviceRoles(session);
		revertRoles();
		//session.setAttribute(RoleHandler.REDIS_SPRING_SESSION_SECURITY_KEY, getAuthentication());
	}
}
