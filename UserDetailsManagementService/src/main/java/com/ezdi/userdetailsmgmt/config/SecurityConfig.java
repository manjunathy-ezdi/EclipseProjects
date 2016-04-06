package com.ezdi.userdetailsmgmt.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.ezdi.sessionMgmt.logger.LoggerUtil;



@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
		.and().authorizeRequests()
		.antMatchers("/login","/","/logout").permitAll()
		.antMatchers("/user/add**").hasAuthority("ROLE_ADD_PERMISSION")
		.antMatchers(HttpMethod.POST,"/user/edit**").hasAnyAuthority("ROLE_ADD_PERMISSION","ROLE_EDIT_PERMISSION")
		.antMatchers("/user/delete**").hasAuthority("ROLE_DELETE_PERMISSION")
		.antMatchers(HttpMethod.GET,"/user**").hasAnyAuthority("ROLE_READ_PERMISSION")
		.anyRequest().authenticated();
	}
	
	

	private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class);
	
	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	@Qualifier("sessionRegistry")
	SessionRegistry sessionRegistry;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
			throws Exception {
		LOGGER.info(LoggerUtil.getMessage("Inside configureGlobalSecurity()"));
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder());
		LOGGER.info(LoggerUtil.getMessage("Exiting configureGlobalSecurity()"));
	}

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
	
	
	
	public SessionRegistry sessionRegistryImpl(){
		return new SessionRegistryImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	private Filter csrfHeaderFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request,
					HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
						.getName());
				if (csrf != null) {
					Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
					String token = csrf.getToken();
					if (cookie == null || token != null
							&& !token.equals(cookie.getValue())) {
						cookie = new Cookie("XSRF-TOKEN", token);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
				filterChain.doFilter(request, response);
			}
		};
		
	}
	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
	@RequestMapping("/token")
	@ResponseBody
	public Map<String,String> token(HttpSession session) {
		LOGGER.info(LoggerUtil.getMessage("Inside token() Token - "+session.getId()));
		return Collections.singletonMap("token", session.getId());
	}	

}
