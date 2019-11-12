package com.acme.common.business;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acme.common.service.AbstractCommand;

public interface CommandHandler {

	static class HandlingContext{
		public final List<Runnable> afterCommit = new ArrayList<>();
	}

	void handle(AbstractCommand command, HandlingContext handlingContext );
	
	@Component
	@Lazy
	@Transactional(propagation = Propagation.MANDATORY)
	@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE)
	public @interface Handler {
		String value() default "";
	}
}