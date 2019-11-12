package com.acme.common.service.impl;

import com.acme.common.business.exception.CommandException;
import com.acme.common.service.AbstractCommand;
import com.acme.common.service.ExceptionHandler;
import com.acme.common.service.CommandProcessor;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

//@service n'est pas obligatoire parceque on utilise import car il est hors package de la appconfig
public class CommandProcessorImpl implements CommandProcessor {

    // TODO 1 : recevoir par injection le logger

	private final Logger logger;
    
    private List<ExceptionHandler> exHandlers;

    // TODO 2 : recevoir par injection l'applicationContext
    private final ApplicationContext ctx;

    public CommandProcessorImpl(Logger logger, ApplicationContext ctx) {
        this.logger = logger;
        this.ctx = ctx;
    }

    @Override
    public <T extends AbstractCommand> T process(T command) {
        String cmdId = command.toString();
        logger.info("Welcome "+cmdId+" !!");
         
        try {
            logger.info("Are you ready for a be published as an event, {} ?", cmdId);
            // TODO 3 : validation de l'état de la commande avant traitement
            command.validateStateBeforeHandling();
             logger.info("Ok, puslish an event for {} !", cmdId);
            // TODO 4 : publier la commande en tant qu'évenement dans le contexte applicatif
            ctx.publishEvent(command);
            logger.info("Are you ok {} ?", cmdId);
            // TODO 5 : validation de l'état de la commande après traitement
            command.validateStateAfterHandling();
            logger.info("Bye bye {} !", cmdId);
            return command;
        } catch (Exception e) {
            // si un des eventListener lève une exception, la méthode publishEvent renverra non pas l'exception levée par
            // l'eventListener mais une UndeclaredThrowableException dont l'exception levée par notre listener sera la cause.
            Throwable actualException;
            if(e instanceof UndeclaredThrowableException && e.getCause()!=null){
                actualException = e.getCause();
            }
            else{
                actualException = e;
            }

            if(this.exHandlers != null){
                /*exemple de traitement d'exception : 
                 serialisation de la commande dans un fichier et écriture de la stacktrace dans un autre fichier.
                 Voir DefaultCommandExceptionHandler
                */
                exHandlers.forEach(x -> x.handleException(command, actualException));
            }
            if (actualException instanceof CommandException) {
                logger.warn("hum hum... "+actualException.getMessage());
                throw (CommandException) actualException;
            }
            actualException.printStackTrace();
            logger.error("hum hum... "+actualException.getMessage());
            throw new RuntimeException("Oops, I did it again...");
        }
    }
}
