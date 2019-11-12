package com.acme.common.service;

public abstract class AbstractCommand {

	public void validateStateBeforeHandling() {}
	
	public void validateStateAfterHandling() {}
}
