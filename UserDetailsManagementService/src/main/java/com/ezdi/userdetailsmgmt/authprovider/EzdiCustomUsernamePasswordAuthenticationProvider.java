package com.ezdi.userdetailsmgmt.authprovider;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public abstract class EzdiCustomUsernamePasswordAuthenticationProvider implements AuthenticationProvider{

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
		if(usernamePasswordAuthenticationToken == null){
			 throw new AuthenticationCredentialsNotFoundException("No Authentication found for this token.");
		}
		if(!usernamePasswordAuthenticationToken.isAuthenticated()){
			throw new BadCredentialsException("Unauthenticated Token");
		}
		UsernamePasswordAuthenticationToken newToken = replaceAuthorities(usernamePasswordAuthenticationToken);
		return newToken;
	}
	
	protected abstract UsernamePasswordAuthenticationToken replaceAuthorities(UsernamePasswordAuthenticationToken token);

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	

}
