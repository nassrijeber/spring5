package com.acme.common.service;

public interface ExceptionHandler {

	void handleException(Exception ex, AbstractCommand command);
}
