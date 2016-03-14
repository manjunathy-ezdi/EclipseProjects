package com.ezdi.sessionManagement.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;

import com.ezdi.sessionManagement.db.dao.UsersSaver;
import com.ezdi.sessionManagement.db.dao.implementation.UsersSaverImpl;
import com.ezdi.sessionManagement.db.model.EzdiUser;
import com.ezdi.sessionManagement.event.listener.EzdiAuthenticationFailureEventListener;
import com.ezdi.sessionManagement.filter.security.EzdiSecurityFilter;
import com.ezdi.sessionManagement.service.UsersService;
import com.ezdi.sessionManagement.service.impl.UsersServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public EzdiAuthenticationFailureEventListener ezdiAuthenticationFailureEventListener(){
		return new EzdiAuthenticationFailureEventListener();
	}
	
	@Bean
	public EzdiSecurityFilter ezdiFilter(){
		return new EzdiSecurityFilter();
	}
	
	@Bean
    public SessionFactory sessionFactory(DataSource dataSource){
        //LocalSessionFactoryBuilder localSessionFactoryBuilder = new LocalSessionFactoryBuilder(getDataSource());
        LocalSessionFactoryBuilder localSessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource);
        localSessionFactoryBuilder.addAnnotatedClasses(EzdiUser.class);
        localSessionFactoryBuilder.addProperties(getHibernateProperties());
        return localSessionFactoryBuilder.buildSessionFactory();
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return properties;
    }
    
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }
	
	@Bean
	public UsersSaver usersSaver(){
		return new UsersSaverImpl();
	}
	
	@Bean
	public UsersService usersService(){
		UsersService ret = new UsersServiceImpl();
		return ret;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().requestCache().requestCache(new NullRequestCache())
				.and().httpBasic();
		http.addFilterAfter(ezdiFilter(), LogoutFilter.class);
	}
	
	@Autowired
	DataSource dataSource;
	
	@Resource(name="userDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService);
		//auth.jdbcAuthentication().dataSource(dataSource);
				//.usersByUsernameQuery("select username,password, enabled from users where username=?")
				//.authoritiesByUsernameQuery("select username, role from user_roles where username=?");
	}
}
