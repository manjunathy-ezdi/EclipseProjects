package com.ezdi.userdetailsmgmt.config;

import org.apache.log4j.Logger;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;

import com.ezdi.sessionMgmt.logger.LoggerUtil;

public class ExpiringSessionConfig<S extends ExpiringSession> {
	private SessionRepository<S> repository;
	private final Logger LOGGER = Logger.getLogger(ExpiringSessionConfig.class);

	public void setInactiveInterval() {
		LOGGER.info(LoggerUtil.getMessage("Inside setInactiveInterval()"));
		S toSave = repository.createSession();
		toSave.setMaxInactiveIntervalInSeconds(300);
		repository.save(toSave);
		LOGGER.info(LoggerUtil.getMessage("Existing setInactiveInterval()"));
	}

}