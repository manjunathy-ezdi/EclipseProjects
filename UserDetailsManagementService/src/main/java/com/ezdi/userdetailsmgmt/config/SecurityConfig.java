package com.ezdi.userdetailsmgmt.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

import com.ezdi.userdetailsmgmt.authprovider.impl.EzdiCustomUsernamePasswordAuthenticationProviderImpl;
import com.ezdi.userdetailsmgmt.filter.EzdiCustomRoleReplacementFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableGlobalAuthentication
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		//.httpBasic().disable()
		.authorizeRequests()//.anyRequest().authenticated();
		.antMatchers("/user/add**").hasAuthority("ROLE_ADD_PERMISSION")
		.antMatchers(HttpMethod.POST,"/user/edit**").hasAnyAuthority("ROLE_ADD_PERMISSION","ROLE_EDIT_PERMISSION")
		.antMatchers(HttpMethod.DELETE, "/user/user**").hasAuthority("ROLE_DELETE_PERMISSION")
		.antMatchers(HttpMethod.GET,"/user/user**").hasAnyAuthority("ROLE_READ_PERMISSION")
		.antMatchers("/user/me").permitAll()
		.anyRequest().authenticated()
		.and()
		//.addFilterBefore(ezdiCustomRoleFilter(), UsernamePasswordAuthenticationFilter.class);
		//.addFilterAfter(ezdiCustomRoleFilter(), SwitchUserFilter.class)
		.addFilterBefore(ezdiCustomRoleFilter(), AnonymousAuthenticationFilter.class)
		.csrf().disable();
		//.authenticationProvider(ezdiAuthenticationProvider())
		
		//http.addFilterBefore(ezdiCustomFilter, BasicAuthenticationFilter.class);
		//http.addFilterBefore(ezdiCustomFilter, UsernamePasswordAuthenticationFilter.class);
		//http.addFilterAfter(ezdiCustomFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	/*
	@Bean
	public EzdiCustomFilter ezdiCustomFilter(){
		return new EzdiCustomFilter();
	}
	*/
	
	@Bean
	public EzdiCustomRoleReplacementFilter ezdiCustomRoleFilter(){
		return new EzdiCustomRoleReplacementFilter();
	}
	

	private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class);
	

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		LOGGER.info("Inside configureGlobalSecurity()");
		//auth.authenticationProvider(ezdiAuthenticationProvider());
		LOGGER.info("Exiting configureGlobalSecurity()");
	}
	
	/*
	@Bean
	public AuthenticationProvider ezdiAuthenticationProvider(){
		return new EzdiCustomUsernamePasswordAuthenticationProviderImpl();
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
