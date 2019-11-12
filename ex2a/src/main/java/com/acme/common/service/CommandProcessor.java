package com.acme.common.service;

public interface CommandProcessor {

	<T extends AbstractCommand> T process(T command) ;
}
