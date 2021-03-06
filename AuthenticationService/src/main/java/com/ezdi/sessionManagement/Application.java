package com.ezdi.sessionManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {
	
	public static int MAX_LOGIN_ATTEMPTS=3;
	public static double MAX_USERACCOUNT_LOCKED_TIME_IN_HOURS = 0.25;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
