package com.ezdi.userdetailsmgmt.authprovider.impl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.ezdi.userdetailsmgmt.authprovider.EzdiCustomUsernamePasswordAuthenticationProvider;

public class EzdiCustomUsernamePasswordAuthenticationProviderImpl extends EzdiCustomUsernamePasswordAuthenticationProvider{

	@Override
	protected UsernamePasswordAuthenticationToken replaceAuthorities(UsernamePasswordAuthenticationToken token) {
		
		return null;
	}
	
	

}
