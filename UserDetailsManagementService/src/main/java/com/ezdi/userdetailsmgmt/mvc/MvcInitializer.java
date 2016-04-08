package com.ezdi.userdetailsmgmt.mvc;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.ezdi.userdetailsmgmt.config.HttpSessionConfig;
import com.ezdi.userdetailsmgmt.config.SecurityConfig;

public class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {SecurityConfig.class, HttpSessionConfig.class};
	}
	

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { MvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
