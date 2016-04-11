package com.ezdi.userdetailsmgmt.filter.rolehandler.impl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.ezdi.userdetailsmgmt.persistence.Role;
import com.ezdi.userdetailsmgmt.repository.RoleRepository;

@Component
public class EzdiRoleHandlerImpl extends RoleHandlerImpl{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	protected Collection<String> getRoles(String roleName) {
		Role role = roleRepository.findByName(roleName);
		Collection<String> permitlist = new HashSet<String>();
		if(role.isAddPermission()){
			permitlist.add("ROLE_ADD_PERMISSION");
		}
		if(role.isEditPermission()){
			permitlist.add("ROLE_EDIT_PERMISSION");
		}
		if(role.isDeletePermission()){
			permitlist.add("ROLE_DELETE_PERMISSION");
		}
		if(role.isReadPermission()){
			permitlist.add("ROLE_READ_PERMISSION");
		}
		return permitlist;
	}
	
	

}
