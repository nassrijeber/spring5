package com.acme.common.business;

import com.acme.common.service.AbstractCommand;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public interface CommandHandler {

	void handle(AbstractCommand command);

	@Component
	@Lazy
	@Retention(RetentionPolicy.RUNTIME)
	@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
	@interface Handler{
		String value() default "";
	}
}
