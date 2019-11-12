package com.acme.ex2.business.impl;

import com.acme.common.business.CommandHandler;
import com.acme.ex2.ApplicationProperties;
import com.acme.ex2.service.command.TranslationCommand;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

@Component
@Lazy
public class EnglishToSpanishTranslationCommandHandler implements CommandHandler<TranslationCommand> {

    private final ApplicationProperties applicationProperties;

    private final Logger logger;

    public EnglishToSpanishTranslationCommandHandler(ApplicationProperties applicationProperties, Logger logger) {
        this.applicationProperties = applicationProperties;
        this.logger = logger;
    }

    @Override
    @EventListener(condition = "#command.langIn=='en' && #command.langOut=='sp'")
    public void handle(TranslationCommand command) {
        logger.info("I will try to handle {}", command.toString());

        // traitement sémantique, syntaxique (spécifique à une traduction en-sp), etc.. sur command.textIn
        // accès au référentiel des mots et récupération du résultat
        Path path = Paths.get(this.applicationProperties.getFolder(), "en-sp.txt");
        try {
            ArrayList<String> wordsOut = new ArrayList<>();
            for (String word : command.getTextIn().split(" ")) {
                Optional<String> result = Files.lines(path)
                        .map(line -> line.split(";"))
                        .filter(columns -> columns[0].equals(word))
                        .map(columns -> columns[1])
                        .findAny();
                wordsOut.add(result.orElse("!!" + word + "!!"));
            }

            // traitement sémantique, syntaxique (spécifique à une traduction en-sp), etc.. sur le résultat
            // utilisation du résultat pour valoriser la propriété textOut
            command.setTextOut(String.join(" ", wordsOut));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
