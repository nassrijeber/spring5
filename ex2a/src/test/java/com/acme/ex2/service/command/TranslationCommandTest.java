package com.acme.ex2.service.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.acme.ex2.ApplicationConfig;
import org.junit.jupiter.api.Test;

import com.acme.common.service.CommandProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {ApplicationConfig.class})
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
			this.processor.process(cmd);
//			assertEquals("salut", cmd.getTextOut());
			System.out.println("***********************************");
			Thread.sleep(2000);
		}
	}	
}
