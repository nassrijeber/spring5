package com.acme.ex2.business.impl;

import com.acme.common.business.CommandHandler;
import com.acme.common.business.CommandHandler.Handler;
import com.acme.common.service.AbstractCommand;
import com.acme.ex2.ApplicationProperties;
import com.acme.ex2.service.command.TranslationCommand;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

@Handler
public class FrenchToSpanishTranslationCommandHandler implements CommandHandler {

	private final ApplicationProperties applicationProperties;

	private final Logger logger;

	public FrenchToSpanishTranslationCommandHandler(ApplicationProperties applicationProperties, Logger logger) {
		this.applicationProperties = applicationProperties;
		this.logger = logger;
	}

	@Override
	@EventListener(condition = "#command.langIn=='fr' && #command.langOut=='sp'")
	public void handle(AbstractCommand command) {
		if (!(command instanceof TranslationCommand)) {
			logger.warn("I don't know how to handle a command of type {}", command.getClass().getName());
			return;
		}

		TranslationCommand cmd = (TranslationCommand) command;

		if (!("fr".equals(cmd.getLangIn()) && "sp".equals(cmd.getLangOut()))) {
			logger.debug("do nothing, don't know how to handle a translation from {} to {}", cmd.getLangIn(), cmd.getLangOut());
			return;
		}
		logger.info("I can take care of {}", command.toString());
		// traitement sémantique, syntaxique (spécifique à une traduction fr-sp), etc.. sur command.textIn
		// accès au référentiel des mots et récupération du résultat
		Path path = Paths.get(this.applicationProperties.getFolder(), "fr-sp.txt");
		try {
			ArrayList<String> wordsOut = new ArrayList<>();
			for (String word : cmd.getTextIn().split(" ")) {
				Optional<String> result = Files.lines(path)
						.map(line -> line.split(";"))
						.filter(columns -> columns[0].equals(word))
						.map(columns -> columns[1]).findAny();
				wordsOut.add(result.orElse("!!" + word + "!!"));
			}

			// traitement sémantique, syntaxique (spécifique à une traduction fr-sp), etc.. sur le résultat
			// utilisation du résultat pour valoriser la propriété textOut
			cmd.setTextOut(String.join(" ", wordsOut));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
