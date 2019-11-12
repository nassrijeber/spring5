package com.acme.ex2.service.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.acme.ex2.ApplicationConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.acme.common.service.impl.CommandProcessorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.Closeable;
import java.io.IOException;

@SpringJUnitConfig(classes = {ApplicationConfig.class})
class TranslationCommandTestWithoutSpringExtension {

	@Autowired
	private static CommandProcessorImpl processor;

	// à décommenter lorsque les dépendances vers spring-context et spring-test ont été ajoutées
	@Autowired
    private static ApplicationContext ctx;
    
    @BeforeAll
    static void setup() {
    	ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    	processor = ctx.getBean(CommandProcessorImpl.class);
    }
    
    @AfterAll
    static void shutdown() throws IOException {
    	if(ctx instanceof Closeable) {
    		((Closeable)ctx).close();
    	}
    }

	@Test
	void testProcess() {
		TranslationCommand cmd = new TranslationCommand();
		cmd.setTextIn("hello");
		cmd.setLangIn("en");
		cmd.setLangOut("fr");

		cmd = processor.process(cmd);
		assertEquals("salut", cmd.getTextOut());
	}
}