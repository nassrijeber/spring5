package com.acme.common.business.exception;

public class CommandException extends RuntimeException {

	private static final long serialVersionUID = 1125374470624772351L;

	public CommandException(String message) {
		super(message);
	}
}
