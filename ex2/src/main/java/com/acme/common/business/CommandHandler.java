package com.acme.common.business;

import com.acme.common.service.AbstractCommand;

public interface CommandHandler<T extends AbstractCommand> {

	public void handle(T command) ;
}
