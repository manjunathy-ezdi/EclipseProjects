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
import org.springframework.stereotype.Component;

import com.ezdi.userdetailsmgmt.UserDetailsMgmtApplication;
import com.ezdi.userdetailsmgmt.filter.rolehandler.RoleHandler;
import com.ezdi.userdetailsmgmt.persistence.Role;
import com.ezdi.userdetailsmgmt.repository.RoleRepository;


@Component
public class RoleHandlerImpl implements RoleHandler {
	
	
	public RoleHandlerImpl() {
		init();
	}
	
	public void init(){
		//newRoles = new HashSet<GrantedAuthority>();
	}
	
	@Autowired
	private RoleRepository roleRepository;
	
	private Authentication originalAuth;
	
	private Authentication newAuth;
	
	public String getRoleAttribute(){
		return ROLE_ATTRIBUTE_PREFIX+UserDetailsMgmtApplication.MICROSERVICE_ID;
	}
	
	/*
	 * SETTING NEW ROLES
	 */
	
	private boolean replaceBySessionAttribute(SecurityContext sc, HttpSession session){
		if(sc != null && session != null){
			Collection<GrantedAuthority> newRoles = (Collection<GrantedAuthority>) session.getAttribute(getRoleAttribute());
			if(newRoles!= null && !newRoles.isEmpty()){
				makeNewAuth(newRoles);
				sc.setAuthentication(newAuth);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void replaceWithNewRoles(SecurityContext sc, HttpSession session){
		if(sc != null && session != null){
			originalAuth = sc.getAuthentication();
			if(replaceBySessionAttribute(sc, session)){}
			else{
				replaceByRepository(sc,session);
			}
		}
	}
	
	private void makeNewAuth(Collection<GrantedAuthority> newRoles){
		newAuth = new UsernamePasswordAuthenticationToken(originalAuth.getPrincipal(), originalAuth.getCredentials(), newRoles);
	}
	
	private boolean replaceByRepository(SecurityContext sc, HttpSession session){
		if(sc != null && session != null && originalAuth != null){
			Collection<? extends GrantedAuthority> originalRoles = originalAuth.getAuthorities();
			Collection<GrantedAuthority> newRoles = new HashSet<GrantedAuthority>();
			if(originalRoles != null && !originalRoles.isEmpty()){
				for(GrantedAuthority each: originalRoles){
					newRoles.addAll(setOfAuthorities(each.getAuthority()));
				}
				makeNewAuth(newRoles);
				sc.setAuthentication(newAuth);
				return true;
			}
		}
		return false;
	}
	

	private Collection<SimpleGrantedAuthority> setOfAuthorities(String roleName) {
		Role role = roleRepository.findByName(roleName);
		Collection<SimpleGrantedAuthority> permitlist = new HashSet<SimpleGrantedAuthority>();
		if(role.isAddPermission()){
			permitlist.add(new SimpleGrantedAuthority("ROLE_ADD_PERMISSION"));
		}
		if(role.isEditPermission()){
			permitlist.add(new SimpleGrantedAuthority("ROLE_EDIT_PERMISSION"));
		}
		if(role.isDeletePermission()){
			permitlist.add(new SimpleGrantedAuthority("ROLE_DELETE_PERMISSION"));
		}
		if(role.isReadPermission()){
			permitlist.add(new SimpleGrantedAuthority("ROLE_READ_PERMISSION"));
		}
		return permitlist;
	}
	
	
	/*
	 * SETTING BACK OLD ROLES
	 */
	
	private void updateMicroserviceRoles(SecurityContext sc, HttpSession session){
		if(sc != null && session != null){
			if(newAuth != null){
				session.setAttribute(getRoleAttribute(), newAuth.getAuthorities());
			}
		}
	}
	
	
	@Override
	public void replaceWithOldRoles(SecurityContext sc, HttpSession session){
		if(sc != null && session != null){
			sc.setAuthentication(originalAuth);
			updateMicroserviceRoles(sc, session);
			newAuth = null;
		}
	}
}
