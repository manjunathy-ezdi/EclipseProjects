package com.ezdi.sessionManagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ezdi.sessionManagement.Application;
import com.ezdi.sessionManagement.db.dao.UsersSaver;
import com.ezdi.sessionManagement.db.model.EzdiUser;
import com.ezdi.sessionManagement.service.UsersService;

//@Service("usersService")
public class UsersServiceImpl implements UsersService{
	
	/*
	public UsersServiceImpl(UsersSaver usersSaver){
		this.usersSaver = usersSaver;
	}
	*/
	
	private UsersSaver usersSaver;
	
	public UsersSaver getUsersSaver() {
		return usersSaver;
	}

	@Autowired
	public void setUsersSaver(UsersSaver usersSaver) {
		this.usersSaver = usersSaver;
	}

	public boolean checkAndIncrementLoginAttempts(String username) throws UsernameNotFoundException{
		EzdiUser user = usersSaver.getByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("User "+username+" does not exist.");
		}
		int attempts = user.getLoginAttempts()+1;
		if(attempts == Application.MAX_LOGIN_ATTEMPTS){
			user.setLoginAttempts(0);
			user.setEnabled(false);
			usersSaver.save(user);
			return false;
		}
		else{
			user.setLoginAttempts(attempts);
			usersSaver.save(user);
			return true;
		}
	}

	public void resetLoginAttempts(String username) throws UsernameNotFoundException {
		EzdiUser user = usersSaver.getByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("User "+username+" does not exist.");
		}
		user.setLoginAttempts(0);
		usersSaver.save(user);
	}

	public EzdiUser getUserByUsername(String username) throws UsernameNotFoundException {
		EzdiUser ezdiUser = usersSaver.getByUsername(username);
		if(ezdiUser == null){
			throw new UsernameNotFoundException("User "+username+" does not exist.");
		}
		return ezdiUser;
	}

}
