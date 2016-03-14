package com.ezdi.sessionManagement.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ezdi.sessionManagement.Application;
import com.ezdi.sessionManagement.db.dao.UsersSaver;
import com.ezdi.sessionManagement.db.model.EzdiUser;
import com.ezdi.sessionManagement.service.UsersService;
import com.ezdi.sessionManagement.util.DateUtil;

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
		user.setLastFailedLoginAttemptDate(Calendar.getInstance().getTime());
		user.setLoginAttempts(attempts);
		if(attempts == Application.MAX_LOGIN_ATTEMPTS){
			user.setLocked(true);
		}
		usersSaver.save(user);
		return !user.isLocked();
	}
	
	public void resetLockedUser(String username) throws UsernameNotFoundException{
		EzdiUser user = usersSaver.getByUsername(username);
		resetLockedUser(user);
	}
	
	public void resetLockedUser(EzdiUser user) throws UsernameNotFoundException{
		if(user == null){
			throw new UsernameNotFoundException("User does not exist.");
		}
		user.setLocked(false);
		user.setLoginAttempts(0);
		usersSaver.save(user);
	}

	public EzdiUser getUserByUsername(String username) throws UsernameNotFoundException {
		EzdiUser ezdiUser = usersSaver.getByUsername(username);
		if(ezdiUser == null){
			throw new UsernameNotFoundException("User "+username+" does not exist.");
		}
		ezdiUser = processUserBeforeProgressing(ezdiUser);
		return ezdiUser;
	}
	
	private EzdiUser processUserBeforeProgressing(EzdiUser ezdiUser) {
		if(ezdiUser == null) return null;
		if(ezdiUser.isLocked()){
			double hoursSinceLastLoginAttempt = DateUtil.differenceInHours(Calendar.getInstance().getTime(), ezdiUser.getLastFailedLoginAttemptDate());
			if(hoursSinceLastLoginAttempt > Application.MAX_USERACCOUNT_LOCKED_TIME_IN_HOURS){
				resetLockedUser(ezdiUser);
			}
		}
		return ezdiUser;
	}
}
