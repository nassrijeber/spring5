package com.acme.common.service;

import java.util.List;

import com.acme.common.business.CommandHandler;

public interface CommandRouting {

	List<CommandHandler> getHandlers(AbstractCommand cmd);

}
