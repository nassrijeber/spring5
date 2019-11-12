package com.acme.common.service;

public interface CommandPreHandler {

	void beforeHandle(AbstractCommand command);
}
