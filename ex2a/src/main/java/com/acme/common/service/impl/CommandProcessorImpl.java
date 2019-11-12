package com.acme.common.service.impl;

import com.acme.common.business.CommandHandler;
import com.acme.common.business.exception.CommandException;
import com.acme.common.service.AbstractCommand;
import com.acme.common.service.AbstractCommand.Usecase;
import com.acme.common.service.ExceptionHandler;
import com.acme.common.service.CommandProcessor;
import org.slf4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CommandProcessorImpl implements CommandProcessor {

    // TODO 1 : injection du logger
    private final Logger logger;

    // TODO 2 : injection de l'ApplicationContext
    private final ApplicationContext ctx;

    private List<ExceptionHandler> exHandlers;

    public CommandProcessorImpl(Logger logger, ApplicationContext ctx) {
        this.logger = logger;
        this.ctx = ctx;
    }

    @Override
    @Cacheable(cacheNames = "commands")
    public <T extends AbstractCommand> T process(T command) {
        
        String cmdId = command.toString();
        logger.info("Received a command of type {} : {}", command.getClass().getSimpleName(), cmdId);
        try {
        	logger.info("Is {} valid before handling ?", cmdId);
            // TODO 3 : validation de l'état avant traitement
            command.validateStateBeforeHandling();
            logger.info("Identify which handlers will handle {}", cmdId);
         
            Usecase usecase = command.getClass().getAnnotation(Usecase.class);
            
            List<CommandHandler> handlersForThisCommand = Stream.of(usecase.handlers())
                    .map(x -> ctx.getBean(x))
                    .collect(Collectors.toList());
            
            /* avant java 8
            List<CommandHandler> handlersForThisCommand = new ArrayList<>();
            
            for(Class<? extends CommandHandler> clazz : usecase.handlers()){
            	CommandHandler impl = ctx.getBean(clazz);
            	handlersForThisCommand.add(impl);
            }*/

            logger.info("{} handlers will deal with {}", handlersForThisCommand.size(), cmdId);
            if(!usecase.parallelHandling()){
            	for (CommandHandler handler : handlersForThisCommand) {
            		logger.info("ask {} to handle {}", handler.getClass().getName(), cmdId);
                	// TODO 4 : appel de la méthode handle sur handler
                    handler.handle(command);
            	}
            }
            else{            	
            	
                CompletableFuture<?>[] workers = handlersForThisCommand.stream()
                        .map(x -> CompletableFuture.runAsync(()-> x.handle(command)))
                        .toArray(n -> new CompletableFuture[n]);
				
            	/* sans stream ni lambda
            	CompletableFuture<?>[] workers = new CompletableFuture<?>[handlersForThisCommand.size()];
            	for(int i = 0; i < handlersForThisCommand.size(); i++) {
            		CommandHandler handler = handlersForThisCommand.get(i);
            		CompletableFuture<Void> worker = CompletableFuture.runAsync(()->handler.handle(command));
            		workers[i] = worker;
            	}
            	*/
	            logger.info("waiting for the {} handlers to complete", workers.length);
	            CompletableFuture.allOf(workers).join();
	            logger.info("command handling completed");
            }
            
            logger.info("is {} valid after handling ?", cmdId);
            // TODO 5 : validation de l'état après traitement
            command.validateStateAfterHandling();
            logger.info("yes, {} is valid, return it", cmdId);
            return command;
        } catch (Exception e) {
            if(this.exHandlers != null){
                /*exemple d'implémentation de CommandExceptionHandler traitement d'exception : 
                 serialisation de la commande dans un fichier sous le nom cmdId.xml
                 et écriture de la stacktrace dans un fichier sous le nom cmdId.stacktrace.txt
                 Voir DefaultCommandExceptionHandler
                */

                exHandlers.forEach(x -> x.handleException(command, e));
            }
            if (e instanceof CommandException) {
                logger.warn("hum hum... "+e.getMessage());
                throw e;
            }
            e.printStackTrace();
            logger.error("hum hum... "+e.getMessage());

            throw new RuntimeException("Oops, I did it again...");
        }
    }
}
