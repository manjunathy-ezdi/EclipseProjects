package com.ezdi.dummy.config;

import org.apache.log4j.Logger;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;

public class ExpiringSessionConfig<S extends ExpiringSession> {
	private SessionRepository<S> repository;
	private final Logger LOGGER = Logger.getLogger(ExpiringSessionConfig.class);

	public void setInactiveInterval() {
		LOGGER.info("Inside setInactiveInterval()");
		/*
		S toSave = repository.createSession();
		toSave.setMaxInactiveIntervalInSeconds(300);
		repository.save(toSave);
		*/
		LOGGER.info("Exiting setInactiveInterval()");
	}

}