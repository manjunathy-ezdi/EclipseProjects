package com.ezdi.springsecurity.securityhandler.authenticationprovider;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class EzdiUsernamePasswordAuthenticationProvider implements AuthenticationProvider{

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
		if(usernamePasswordAuthenticationToken == null){
			 throw new AuthenticationCredentialsNotFoundException("No Authentication found for this token.");
		}
		if(!usernamePasswordAuthenticationToken.isAuthenticated()){
			throw new BadCredentialsException("Unauthenticated Token");
		}
		PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(usernamePasswordAuthenticationToken.getPrincipal(), usernamePasswordAuthenticationToken.getCredentials(), usernamePasswordAuthenticationToken.getAuthorities());
		preAuthenticatedAuthenticationToken.setDetails(usernamePasswordAuthenticationToken.getDetails());
		return preAuthenticatedAuthenticationToken;		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (authentication.equals(UsernamePasswordAuthenticationToken.class));
	}
	
}
