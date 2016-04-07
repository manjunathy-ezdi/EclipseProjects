package com.ezdi.userdetailsmgmt.config;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/user/add**").hasAuthority("ROLE_ADD_PERMISSION")
		.antMatchers(HttpMethod.POST,"/user/edit**").hasAnyAuthority("ROLE_ADD_PERMISSION","ROLE_EDIT_PERMISSION")
		.antMatchers(HttpMethod.DELETE, "/user**").hasAuthority("ROLE_DELETE_PERMISSION")
		.antMatchers(HttpMethod.GET,"/user**").hasAnyAuthority("ROLE_READ_PERMISSION")
		.anyRequest().authenticated();
	}
	
	

	private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class);
	

	/*
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
			throws Exception {
		LOGGER.info("Inside configureGlobalSecurity()");
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder());
		LOGGER.info("Exiting configureGlobalSecurity()");
	}
	*/

	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		LOGGER.info(LoggerUtil.getMessage("Configuraing Security Inside - #$#SecurityConfig:configurekkkk"));
		http.httpBasic()
		//.and().logout()
		.and().authorizeRequests()
		.antMatchers("/login","/","/logout").permitAll()
		.anyRequest().authenticated()
		.and().formLogin()//.loginPage("/login")
		.usernameParameter("username").passwordParameter("password")
		.and()
		.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry)
		.and()
		.and()
		.csrf().disable();
		
		//.and().csrf()
		//.csrfTokenRepository(csrfTokenRepository()).and()
		//.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
		 * 
		//above is working one 15/March
		LOGGER.info(LoggerUtil.getMessage("Exiting #$#SecurityConfig:configurekkkk"));
		
		//http.addFilterBefore(ezdiDebugFilter(), ConcurrentSessionFilter.class);
	}
	*/	

}
