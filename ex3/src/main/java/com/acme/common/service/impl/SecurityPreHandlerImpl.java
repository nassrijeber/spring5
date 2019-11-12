package com.acme.common.service.impl;

import org.slf4j.Logger;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;

import com.acme.common.service.AbstractCommand;
import com.acme.common.service.AbstractCommand.Usecase;
import com.acme.common.service.CommandPreHandler;

public class SecurityPreHandlerImpl implements CommandPreHandler {

	private final Logger logger;
	
	public SecurityPreHandlerImpl(Logger logger) {
		super();
		this.logger = logger;
	}

	@Override
	public void beforeHandle(AbstractCommand command) {
		
		if(command.getClass().isAnnotationPresent(Usecase.class)){
			Usecase usecase = command.getClass().getAnnotation(Usecase.class);
			
			if(usecase.secured()){
				String requiredAuthority = usecase.requiredAuthority();
				String commandClassName = command.getClass().getSimpleName();
				/*
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				
				if(authentication == null){
					logger.warn("anonymous users are not allowed to process {}", commandClassName);
					throw new SecurityException();
				}
				logger.info("{} is secured, check if {} can process it", commandClassName, authentication.getName() );
				boolean hasAccess = authentication.getAuthorities()
						.stream()
						.map(x -> x.getAuthority())
						.anyMatch(x -> x.equals("*") || x.equals(requiredAuthority));
				if(!hasAccess){
					logger.warn("no, {} cannot process {}", authentication.getName(), commandClassName);
					throw new SecurityException();
				}
				else{
					logger.info("yes, {} can process {}", authentication.getName(), commandClassName);
				}*/
			}
		}
	}
}
