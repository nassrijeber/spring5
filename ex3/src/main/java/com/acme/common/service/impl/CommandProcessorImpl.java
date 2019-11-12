package com.acme.common.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.acme.common.business.CommandException;
import com.acme.common.business.CommandHandler;
import com.acme.common.business.CommandHandler.HandlingContext;
import com.acme.common.service.AbstractCommand;
import com.acme.common.service.AbstractCommand.Usecase;
import com.acme.common.service.CommandProcessor;
import com.acme.common.service.ExceptionHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class CommandProcessorImpl implements CommandProcessor {

	private final Logger logger;

	private final List<ExceptionHandler> exHandlers;

	private final ApplicationContext ctx;

// TODO : injections de dépendances pour les 3 champs ci dessus.
	public CommandProcessorImpl(Logger logger, List<ExceptionHandler> exHandlers, ApplicationContext ctx) {
		this.logger = logger;
		this.exHandlers = exHandlers;
		this.ctx = ctx;
	}

//	@Autowired
//	private  Logger logger;
//	@Autowired
//	private  List<ExceptionHandler> exHandlers;
//	@Autowired
//	private  ApplicationContext ctx;

	@Override
	@Transactional(propagation= Propagation.REQUIRED)
	public <T extends AbstractCommand> T process(T command){

		String cmdId = command.toString();
		logger.info("Received a command of type {} : {}", command.getClass().getSimpleName(), cmdId);

		HandlingContext handlingContext = new HandlingContext();

		try {
			logger.info("Is {} valid before handling ?", cmdId);
			// TODO : validation de l'état avant traitement
			command.validateStateBeforeHandling();

			logger.info("Identify which handlers will handle {}", cmdId);

			Usecase usecase = command.getClass().getAnnotation(Usecase.class);

			List<CommandHandler> handlersForThisCommand = Stream.of(usecase.handlers())
					.map(x -> ctx.getBean(x))
					.collect(Collectors.toList());

			logger.info("{} handlers will deal with {}", handlersForThisCommand.size(), cmdId);
			if(!usecase.parallelHandling()){
				for (CommandHandler handler : handlersForThisCommand) {
					logger.info("ask {} to handle {}", handler.getClass().getName(), cmdId);
					// TODO : appel de la méthode handle sur handler
					handler.handle(command, handlingContext);
				}
			}
			else{

				CompletableFuture<?>[] futures = handlersForThisCommand.stream()
						.map(h -> CompletableFuture.runAsync(() -> h.handle(command, handlingContext)))
						.toArray(n -> new CompletableFuture[n]);

				logger.info("waiting for the {} handlers to complete", futures.length);
				CompletableFuture.allOf(futures).join();
				logger.info("command handling completed");
			}

			logger.info("is {} valid after handling ?", cmdId);
			// TODO : validation de l'état après traitement
			command.validateStateAfterHandling();
			logger.info("yes, {} is valid, return it", cmdId);

			if(!handlingContext.afterCommit.isEmpty()) {
				TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
					public void afterCommit() {
						handlingContext.afterCommit.forEach(r -> r.run());
					};
				});
			}
			return command;
		} catch (Exception e) {
			/*exemple de traitement d'exception : 
			 serialisation de la commande dans un fichier sous le nom cmd.toString()+".json"
			 et écriture de la stacktrace dans un fichier sous le nom cmd.toString()+".stacktrace.txt"
			 Voir DefaultExceptionHandler
			*/
			exHandlers.forEach(x-> x.handleException(e, command));
			
			if (e instanceof CommandException) {
				logger.warn("hum hum... "+e.getMessage());
				throw e;
			}
			else{
				e.printStackTrace();
				logger.error("hum hum... "+e.getMessage());
				throw new RuntimeException("Oops, I did it again...");
			}
		}
	}

	@Autowired
	private CommandProcessorImpl proxyOnMe;

	public <T extends AbstractCommand> CompletableFuture<T> processAsync(T command){
		return CompletableFuture.supplyAsync(()->proxyOnMe.process(command));
	}
}