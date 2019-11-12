package com.acme.common.service.impl;

import org.slf4j.Logger;

import com.acme.common.service.AbstractCommand;
import com.acme.common.service.ExceptionHandler;

public class DefaultExceptionHandler implements ExceptionHandler {

	private boolean enabled;

	private final Logger logger;
	
	public DefaultExceptionHandler(Logger logger) {
		super();
		this.logger = logger;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	@Override
	public void handleException(Exception ex, AbstractCommand command) {
		logger.info("inside handleException");
		if(enabled){
			logger.info("do exception handling for "+ex.getClass().getName());
			@SuppressWarnings("unused")
			String cmdId = command.toString();
			// serialisation de la commande dans un fichier sous le nom cmdId.xml
			// et écriture de la stacktrace dans un fichier sous le nom cmdId.stacktrace.txt
		}
		else{
			logger.info("do nothing for "+ex.getClass().getName());
		}
	}
}
