package com.ezdi.dummy;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages={"com.ezdi.dummy.*"})
public class MyDummyMicroservice {
	
	private static final Logger LOGGER = Logger.getLogger(MyDummyMicroservice.class);
	public static void main(String[] args) {
		LOGGER.info("Inside #$#UserDetailsMgmtApplication:main()");
		ApplicationContext applicationContext = SpringApplication.run(MyDummyMicroservice.class, args);
		String[] beanNames = applicationContext.getBeanDefinitionNames();
        System.out.println("The beans configured (automatically by spring-boot!!) are: ");
        if(beanNames != null){
            System.out.println("NUMBER : "+beanNames.length);
            Arrays.sort(beanNames);
            for(String each: beanNames){
                System.out.println(each);
            }
        }
        else{
            System.out.println("beanNames is NULL");
        }
		LOGGER.info("Exiting #$#UserDetailsMgmtApplication:main()");
	}
}
