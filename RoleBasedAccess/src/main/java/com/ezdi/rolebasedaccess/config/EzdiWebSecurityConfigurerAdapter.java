package com.ezdi.rolebasedaccess.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import com.ezdi.rolebasedaccess.filter.EzdiCustomRoleReplacementFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableGlobalAuthentication
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class EzdiWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.addFilterBefore(ezdiCustomRoleFilter(), AnonymousAuthenticationFilter.class);
	}
	
	@Bean
	public EzdiCustomRoleReplacementFilter ezdiCustomRoleFilter(){
		return new EzdiCustomRoleReplacementFilter();
	}
	
}
