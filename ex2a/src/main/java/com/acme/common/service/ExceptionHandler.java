package com.acme.common.service;

public interface ExceptionHandler {

	void handleException(AbstractCommand command, Exception e);
	
}
