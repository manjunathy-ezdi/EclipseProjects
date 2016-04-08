package com.ezdi.userdetailsmgmt.authprovider;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public abstract class EzdiCustomUsernamePasswordAuthenticationProvider implements AuthenticationProvider{

	private static final Logger logger = Logger.getLogger(EzdiCustomUsernamePasswordAuthenticationProvider.class); 
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("AuthProvider start");
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
		if(usernamePasswordAuthenticationToken == null){
			 throw new AuthenticationCredentialsNotFoundException("No Authentication found for this token.");
		}
		if(!usernamePasswordAuthenticationToken.isAuthenticated()){
			throw new BadCredentialsException("Unauthenticated Token");
		}
		UsernamePasswordAuthenticationToken newToken = processToken(usernamePasswordAuthenticationToken);
		return newToken;
	}
	
	protected UsernamePasswordAuthenticationToken processToken(UsernamePasswordAuthenticationToken token){
		List<String> authoritiesString = null;
		Collection<GrantedAuthority> initAuthorities = token.getAuthorities();
		Collection<GrantedAuthority> retAuthorities = new HashSet<GrantedAuthority>();
		for(GrantedAuthority each: initAuthorities){
			authoritiesString = listOfAuthorities(each.getAuthority(), -1, -1);
			if(authoritiesString != null && !authoritiesString.isEmpty()){
				for(String eachPerm: authoritiesString){
					retAuthorities.add(new SimpleGrantedAuthority(eachPerm));
					logger.info(eachPerm+" added");
				}
			}
		}
		//UsernamePasswordAuthenticationToken newToken = new UsernamePasswordAuthenticationToken(token.getPrincipal(), token.getCredentials(), retAuthorities);
		UsernamePasswordAuthenticationToken newToken = new UsernamePasswordAuthenticationToken("ezdiUser", "ezdiPassword", retAuthorities);
		newToken.setAuthenticated(true);
		newToken.setDetails(token.getDetails());
		return newToken;
	}
	
	protected abstract List<String> listOfAuthorities(String roleName, long hospitalId, long locationId);

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	

}
