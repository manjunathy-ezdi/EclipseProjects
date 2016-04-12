package com.ezdi.dummy.access;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezdi.dummy.persistence.Role;
import com.ezdi.dummy.repository.RoleRepository;
import com.ezdi.rolebasedaccess.rolehandler.AbstractRoleHandlerImpl;

@Component
public class MyDummyRoleHandler extends AbstractRoleHandlerImpl{

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
