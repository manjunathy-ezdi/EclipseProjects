package com.ezdi.dummy.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.ezdi.rolebasedaccess.config.EzdiWebSecurityConfigurerAdapter;


@Configuration
public class MyDummySecurityConfig extends EzdiWebSecurityConfigurerAdapter {
	
	protected void configure(HttpSecurity http) throws Exception {
		
		/*
		 * VERY IMPORTANT ==> CALL super.configure(http); !!!
		 */
		super.configure(http);
		http.authorizeRequests()
		.antMatchers("/user/add**").hasAuthority("ROLE_ADD_PERMISSION")
		.antMatchers(HttpMethod.POST,"/user/edit**").hasAnyAuthority("ROLE_ADD_PERMISSION","ROLE_EDIT_PERMISSION")
		.antMatchers(HttpMethod.DELETE, "/user/user**").hasAuthority("ROLE_DELETE_PERMISSION")
		.antMatchers(HttpMethod.GET,"/user/user**").hasAnyAuthority("ROLE_READ_PERMISSION")
		.antMatchers("/user/me").permitAll()
		.and()
		.csrf().disable();
	}


	private static final Logger LOGGER = Logger.getLogger(MyDummySecurityConfig.class);
	

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		LOGGER.info("Inside configureGlobalSecurity()");
		//auth.authenticationProvider(ezdiAuthenticationProvider());
		LOGGER.info("Exiting configureGlobalSecurity()");
	}
}
