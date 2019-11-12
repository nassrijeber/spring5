package com.acme.common.service.impl;

import com.acme.common.service.AbstractCommand;
import com.acme.common.service.CommandPostHandler;
import com.acme.common.service.CommandPreHandler;


public class SerializerPrePostHandlerImpl implements CommandPostHandler, CommandPreHandler {

	@Override
	public void beforeHandle(AbstractCommand command) {
		String filename = command.toString() + ".in.xml";
		log(command, filename);
	}

	@Override
	public void afterHandle(AbstractCommand command) {
		String filename = command.toString() + ".out.xml";
		log(command, filename);
	}

	private void log(AbstractCommand command, String filename) {
		System.out.println("log " + command.toString());
	}
}
