package com.acme.ex2.service.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.common.business.exception.CommandException;
import com.acme.common.service.AbstractCommand;
import com.acme.common.service.AbstractCommand.Usecase;
import com.acme.ex2.business.impl.EnglishToFrenchTranslationCommandHandler;
import com.acme.ex2.business.impl.EnglishToSpanishTranslationCommandHandler;
import com.acme.ex2.business.impl.FrenchToSpanishTranslationCommandHandler;

import java.util.Objects;

@Usecase(handlers={
		EnglishToFrenchTranslationCommandHandler.class,
		FrenchToSpanishTranslationCommandHandler.class,
		EnglishToSpanishTranslationCommandHandler.class,
}, parallelHandling = false)
public class TranslationCommand extends AbstractCommand {

	private String textIn;
	private String langIn;
	private String langOut;
	private String textOut;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public int inputHash(){
		return Objects.hash(langIn, langOut, textIn);
	}

	public String getTextIn() {
		return textIn;
	}

	public void setTextIn(String textIn) {
		this.textIn = textIn;
	}

	public String getLangIn() {
		return langIn;
	}

	public void setLangIn(String langIn) {
		this.langIn = langIn;
	}

	public String getLangOut() {
		return langOut;
	}

	public void setLangOut(String langOut) {
		this.langOut = langOut;
	}

	public String getTextOut() {
		return textOut;
	}

	public void setTextOut(String textOut) {
		this.textOut = textOut;
	}
	
	@Override
	public void validateStateBeforeHandling() {
		super.validateStateBeforeHandling();
		if(this.langIn==null){
			throw new CommandException("langIn cannot be null");
		}
		if(this.langOut==null){
			throw new CommandException("langOut cannot be null");
		}
		if(this.textIn==null){
			throw new CommandException("textIn cannot be null");
		}

	}

	@Override
	public void validateStateAfterHandling() {
		super.validateStateAfterHandling();
		if(this.textOut == null){
			logger.warn("textOut could not be computed");
		}
	}
}
