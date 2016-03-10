package com.ezdi.sessionManagement.mvc;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.ezdi.sessionManagement.config.HttpSessionConfig;
import com.ezdi.sessionManagement.config.SecurityConfig;

public class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	// tag::config[]
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {SecurityConfig.class, HttpSessionConfig.class};
	}
	// end::config[]

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { MvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
