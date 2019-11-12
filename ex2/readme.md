# Exercice 2 :

Objectifs :
- ajout des dépendances Maven
- découverte des patterns _command_ et _observer_ (voir le fichier readme-cp.md)
- Spring expression language
- couplage de Spring et Junit
*****

1. Compléter le fichier pom.xml en déclarant les dépendances vers 
   * `org.springframework spring-context`
   * `org.springframework spring-test`
   
2. Créer une classe de configuration `com.acme.ex2.ApplicationConfig`. Celle-ci devra intégrer le fichier `application.properties` afin que les propriétés soient 
	* récupérables par l'environnement Spring (méthode `getProperty` du type `org.springframework.core.env.Environment`)
	* injectables dans nos beans (annotation `@Value`)
	
3. Ajouter une méthode productrice afin que le logger slf4j soient injectable dans nos _beans_. Récupération d'un logger : `Logger logger = LoggerFactory.getLogger`

4. Annoter la classe `ApplicationProperties` pour qu'elle soit un _bean_ Spring et que le champ `folder` soit valorisé sur la base de la propriété `folder` du ficher `application.properties`

5. Annoter les implémentations de `CommandHandler` afin qu'elles soient des _beans_ Spring. Activer l'injection de dépendances sur `logger` et `applicationProperties`.

6. Compléter la classes `CommandProcessorImpl` (suivre les TODO) et les implémentations de l'interface `CommandHandler` (package `com.acme.ex2.domain.business.impl`) afin que la publication de la commande en tant qu'évement depuis la méthode `process` du `CommandProcessorImpl` conduise à invoquer les méthodes `handle` des `CommandHandler`

7. Compléter la classe de configuration `ApplicationConfig` pour que soient ajoutés au contexte spring : 

   * le `CommandProcessorImpl`
   * les `CommandHandler`

8. Compléter le test unitaire `TranslationCommandTest` en couplant Junit à Spring.