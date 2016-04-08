package com.ezdi.userdetailsmgmt.mvc;

import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan
public class MvcConfig extends WebMvcConfigurerAdapter {
	 /*@Bean(name = "dataSource")
	 public DriverManagerDataSource dataSource() {
		 System.out.println("MvcConfig:dataSource");
	     DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	     driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
	     driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/userSessionManagement");
	     driverManagerDataSource.setUsername("root");
	     driverManagerDataSource.setPassword("smp123");
	     
//	   driverManagerDataSource.setUrl("jdbc:mysql://192.168.17.88:3306/ez_users");
//	     driverManagerDataSource.setUsername("root");
//	     driverManagerDataSource.setPassword("P@ssw0rd@123");
	     return driverManagerDataSource;
	 }*/
	
	@Bean(name= DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet(ApplicationContext applicationContext) {
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.setParent(applicationContext);
        //webContext.register(BeanConfiguration.class);
        // webContext.refresh();
        return new DispatcherServlet(webContext);
    }

    @Bean(name=DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME)
    public ServletRegistrationBean dispatcherServletRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet);
        //ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        //servletRegistrationBean.setServlet(dispatcherServlet);
        servletRegistrationBean.addUrlMappings("/");
        servletRegistrationBean.setLoadOnStartup(0);
        servletRegistrationBean.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
        return servletRegistrationBean;
    }
}
