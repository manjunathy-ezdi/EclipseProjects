package com.ezdi.springsecurity.securityhandler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.ezdi.springsecurity.securityhandler.authenticationprovider.EzdiUsernamePasswordAuthenticationProvider;
import com.ezdi.springsecurity.securityhandler.filter.EzdiSecuritySessionFilter;

/**
 * Created by EZDI\manjunath.y on 2/3/16.
 */


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public EzdiSecuritySessionFilter ezdiFilter(){
		return new EzdiSecuritySessionFilter();
	}

	@Bean
	public EzdiUsernamePasswordAuthenticationProvider ezdiUsernamePasswordAuthenticationProvider(){
		return new EzdiUsernamePasswordAuthenticationProvider();
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(ezdiFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    
    protected void configureGlobal(AuthenticationManagerBuilder auth){
    	auth.authenticationProvider(ezdiUsernamePasswordAuthenticationProvider());
    }
}

//public class SecurityConfig {}
