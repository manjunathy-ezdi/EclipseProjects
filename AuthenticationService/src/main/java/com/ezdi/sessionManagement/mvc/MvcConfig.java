package com.ezdi.sessionManagement.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan
public class MvcConfig extends WebMvcConfigurerAdapter {
	 @Bean(name = "dataSource")
	 public DriverManagerDataSource dataSource() {
	     DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	     driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
	     driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/ezdiUserSessionManagement");
	     driverManagerDataSource.setUsername("root");
	     driverManagerDataSource.setPassword("P@ssw0rd@123");
	     return driverManagerDataSource;
	 }
}
