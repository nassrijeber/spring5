package com.acme.common.business;

import com.acme.common.service.AbstractCommand;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public interface CommandHandler {

	public void handle(AbstractCommand command);

	@Retention(RetentionPolicy.RUNTIME)
	@Component
	@Lazy
	public @interface Handler{
		String value() default "";

	}
}
