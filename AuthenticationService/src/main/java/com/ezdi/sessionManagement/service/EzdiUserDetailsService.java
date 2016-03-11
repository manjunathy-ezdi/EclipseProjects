package com.ezdi.sessionManagement.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import com.ezdi.sessionManagement.db.model.EzdiUser;

@Service("userDetailsService")
public class EzdiUserDetailsService extends JdbcUserDetailsManager{
	
	public static final String AUTHORITIES_QUERY_STRING="select username, role from user_roles where username=?"; 
	
	@Autowired
	public EzdiUserDetailsService(DataSource dataSource){
		super();
		setAuthoritiesByUsernameQuery(AUTHORITIES_QUERY_STRING);
		setDataSource(dataSource);
	}

	private UsersService usersService;
	
	public UsersService getUsersService() {
		return usersService;
	}

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EzdiUser user = usersService.getUserByUsername(username);
		Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();
		List<GrantedAuthority> authList;
		if(getEnableAuthorities()) {
			dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));
		}
		if(dbAuthsSet.size() > 0){
			authList = new ArrayList<GrantedAuthority>(dbAuthsSet);
		}
		else{
			authList = null;
		}
		UserDetails userDetails = new User(user.getUsername(),user.getPassword(),user.isEnabled(),true,true,!(user.isLocked()),authList);
		return userDetails;
	}
}
