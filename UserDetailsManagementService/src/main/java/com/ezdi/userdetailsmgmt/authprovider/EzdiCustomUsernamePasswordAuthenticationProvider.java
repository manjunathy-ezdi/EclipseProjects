package com.ezdi.userdetailsmgmt.authprovider;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
	
	protected UsernamePasswordAuthenticationToken replaceAuthorities(UsernamePasswordAuthenticationToken token){
		List<String> authoritiesString = null;
		Collection<GrantedAuthority> initAuthorities = token.getAuthorities();
		Collection<GrantedAuthority> retAuthorities = new HashSet<GrantedAuthority>();
		for(GrantedAuthority each: initAuthorities){
			authoritiesString = listOfAuthorities(each.getAuthority(), -1, -1);
			if(authoritiesString != null && !authoritiesString.isEmpty()){
				for(String eachPerm: authoritiesString){
					retAuthorities.add(new SimpleGrantedAuthority(eachPerm));
				}
			}
		}
		return new UsernamePasswordAuthenticationToken(token.getPrincipal(), token.getCredentials(), retAuthorities);
	}
	
	protected abstract List<String> listOfAuthorities(String roleName, long hospitalId, long locationId);

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	

}
