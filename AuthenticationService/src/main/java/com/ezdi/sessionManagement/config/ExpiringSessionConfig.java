package com.ezdi.sessionManagement.config;

import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;

public class ExpiringSessionConfig<S extends ExpiringSession> {
    private SessionRepository<S> repository; 

    public void setInactiveInterval() {
        S toSave = repository.createSession(); 
        toSave.setMaxInactiveIntervalInSeconds(30); 
        repository.save(toSave); 
    }

}