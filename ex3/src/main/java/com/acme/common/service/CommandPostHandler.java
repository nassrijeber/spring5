package com.acme.common.service;

public interface CommandPostHandler {

	void afterHandle(AbstractCommand command);
}
