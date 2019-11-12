package com.acme.common.service.impl;

import com.acme.common.business.CommandHandler;
import com.acme.common.service.AbstractCommand;
import com.acme.common.service.AbstractCommand.Usecase;
import com.acme.common.service.CommandRouting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultCommandRouting implements CommandRouting {

	private final Map<Class<? extends AbstractCommand>, List<CommandHandler>> routing = new ConcurrentHashMap<>();

	@Autowired
	private ApplicationContext ctx;

	@Override
	public List<CommandHandler> getHandlers(AbstractCommand cmd) {

		List<CommandHandler> handlersForThisCommand = this.routing.computeIfAbsent(cmd.getClass(), c -> {;
			Usecase usecase = cmd.getClass().getAnnotation(Usecase.class);
			return Stream.of(usecase.handlers()).map(x -> ctx.getBean(x)).collect(Collectors.toList());
		});
		return handlersForThisCommand;
	}
}
