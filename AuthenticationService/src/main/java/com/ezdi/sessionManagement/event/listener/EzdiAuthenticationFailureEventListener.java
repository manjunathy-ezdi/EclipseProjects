package com.ezdi.sessionManagement.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;

import com.ezdi.sessionManagement.service.UsersService;

public class EzdiAuthenticationFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent>{

	@Autowired
	private UsersService usersService;
	
	public UsersService getUsersService() {
		return usersService;
	}

	
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		String username = event.getAuthentication().getName();
		boolean isLocked = usersService.checkAndIncrementLoginAttempts(username);		
	}		
}
