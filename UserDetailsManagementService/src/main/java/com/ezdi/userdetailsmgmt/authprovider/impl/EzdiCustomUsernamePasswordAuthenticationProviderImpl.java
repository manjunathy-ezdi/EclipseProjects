package com.ezdi.userdetailsmgmt.authprovider.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ezdi.userdetailsmgmt.authprovider.EzdiCustomUsernamePasswordAuthenticationProvider;
import com.ezdi.userdetailsmgmt.persistence.Role;
import com.ezdi.userdetailsmgmt.repository.RoleRepository;

public class EzdiCustomUsernamePasswordAuthenticationProviderImpl extends EzdiCustomUsernamePasswordAuthenticationProvider{

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	protected List<String> listOfAuthorities(String roleName, long hospitalId, long locationId) {
		Role role = roleRepository.findByUsername(roleName);
		List<String> permitlist = new ArrayList<>();
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
