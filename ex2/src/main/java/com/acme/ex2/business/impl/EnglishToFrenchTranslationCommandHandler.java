package com.acme.ex2.business.impl;

import com.acme.common.business.CommandHandler;
import com.acme.ex2.ApplicationProperties;
import com.acme.ex2.service.command.TranslationCommand;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

@Component
@Lazy
public class EnglishToFrenchTranslationCommandHandler implements CommandHandler<TranslationCommand> {

    private final ApplicationProperties applicationProperties;

    private final Logger logger;

    public EnglishToFrenchTranslationCommandHandler(ApplicationProperties applicationProperties, Logger logger) {
        this.applicationProperties = applicationProperties;
        this.logger = logger;
    }

    @Override
    @EventListener(condition = "#command.langIn=='en' && #command.langOut=='fr'")
    public void handle(TranslationCommand command) {
        logger.info("I will try to handle {}", command.toString());

        // traitement sémantique, syntaxique (spécifique à une traduction en-fr), etc.. sur command.textIn
        // accès au référentiel des mots et récupération de la traduction de chaque mot traduit
        Path path = Paths.get(this.applicationProperties.getFolder(), "en-fr.txt");
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

            // traitement sémantique, syntaxique (spécifique à une traduction en-fr), etc.. sur le résultat
            // utilisation du résultat pour valoriser la propriété textOut
            command.setTextOut(String.join(" ", wordsOut));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
