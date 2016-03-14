package com.ezdi.sessionManagement.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ezdi.sessionManagement.db.model.EzdiUser;

public interface UsersService {
	
	public EzdiUser getUserByUsername(String username) throws UsernameNotFoundException;
	public boolean checkAndIncrementLoginAttempts(String username) throws UsernameNotFoundException;
	public void resetLockedUser(EzdiUser user) throws UsernameNotFoundException;
	public void resetLockedUser(String username) throws UsernameNotFoundException;
}
