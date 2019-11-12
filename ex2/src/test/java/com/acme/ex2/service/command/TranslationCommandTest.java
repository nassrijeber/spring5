package com.acme.ex2.service.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.acme.ex2.ApplicationConfig;
import org.junit.jupiter.api.Test;

import com.acme.common.service.impl.CommandProcessorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {ApplicationConfig.class})
class TranslationCommandTest {

	@Autowired
	private CommandProcessorImpl processor;
	
	@Test
	void testProcess() {
		TranslationCommand cmd = new TranslationCommand();
		cmd.setTextIn("hello");
		cmd.setLangIn("en");
		cmd.setLangOut("fr");

		cmd = this.processor.process(cmd);
		assertEquals("salut", cmd.getTextOut());
	}

	//added by me
	@Test
	void testProcess1() {
		TranslationCommand cmd = new TranslationCommand();
		cmd.setTextIn("salut");
		cmd.setLangIn("fr");
		cmd.setLangOut("sp");

		cmd = this.processor.process(cmd);
		assertEquals("hola", cmd.getTextOut());
	}
}