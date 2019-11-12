package com.acme.common.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.acme.common.business.CommandHandler;

public abstract class AbstractCommand {

	public void validateStateBeforeHandling() {}
	
	public void validateStateAfterHandling() {}
	
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Usecase{
		Class<? extends CommandHandler>[] handlers();
	
		boolean parallelHandling() default false;
	}
}
