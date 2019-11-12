package com.acme.ex2.service.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.acme.ex2.ApplicationConfig;
import org.junit.jupiter.api.Test;

import com.acme.common.service.CommandProcessor;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfig.class)
class TranslationCommandTest {

	@Autowired
	private CommandProcessor processor;
	
	@Test
	void testProcess() throws Exception {
		TranslationCommand cmd = new TranslationCommand();
		cmd.setLangIn("en");
		cmd.setLangOut("fr");
		cmd.setTextIn("hello");
		this.processor.process(cmd);
		assertEquals("salut", cmd.getTextOut());
	}
	
	@Test
	void testProcessInInfiniteLoop() throws Exception {
		while(true) {
			TranslationCommand cmd = new TranslationCommand();
			cmd.setLangIn("en");
			cmd.setLangOut("fr");
			cmd.setTextIn("hello");
			System.out.println("about to call process on "+this.processor.getClass());
			System.out.println(cmd.hashCode());
			cmd = this.processor.process(cmd);
			System.out.println(cmd.hashCode());
			assertEquals("salut", cmd.getTextOut());
			System.out.println("***********************************");
			Thread.sleep(2000);
		}
	}	
}
