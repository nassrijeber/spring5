# Exercice 2a :

Objectifs :
* cache
* Spring expression language
* programmation orientée aspect
* jmx
*****

1. Créer une classe de configuration `com.acme.ex2.ApplicationConfig`. Celle-ci devra intégrer le fichier `application.properties` afin que les propriétés soient 
	* récupérables par l'environnement Spring (méthode `getProperty` du type `org.springframework.core.env.Environment`)
	* injectables dans nos beans (annotation `@Value`)

2. Ajouter une méthode productrice afin que le logger slf4j soient injectable dans nos _beans_. Récupération d'un logger : `Logger logger = LoggerFactory.getLogger`

3. Annoter la classe `ApplicationProperties` pour qu'elle soit un bean Spring et que le champ `folder` soit valorisé sur la base de la propriété `folder` du ficher `application.properties`

4. Annoter les implémentations de `CommandHandler` afin qu'elles soient gérées par Spring

5. Examiner la classe `CommandProcessorImpl`, compléter les TODO

6. Compléter la classe de configuration `ApplicationConfig` pour que soient ajoutés au contexte spring : 

   * le `CommandProcessorImpl` (notre service)
   * les `CommandHandler` (notre couche business)

7. Compléter et lancer le test unitaire `testProcess` de la classe de test `TranslationCommandTest`

8. Déclarer les `CommandHandler` en _lazy_ afin qu'il ne soient construits que la première fois qu'ils sont utilisés.

9. Créer une spécialisation de `@Component` (nommée par exemple `@Handler`) et qui factoriserait `@Component` et `@Lazy`

10. A faire ensemble : partie de cache cache.

11. Créer un aspect `com.acme.ex2.aspect.HandlerPerformanceMonitor` et y injecter un `Logger` afin de mesurer le temps d'exécution des méthodes `handle` des `CommandHandler`. Dans le corps de la méthode responsable de l'interception : 

    ```java
    long n = System.currentTimeMillis();
    // TODO appel du joinpoint
    long elapsedTime = System.currentTimeMillis() - n;
    // log pour indiquer le temps d'exécution de la méthode
    logger.info("it took {}ms to call {}", elaspedTime, pjp.toString());
    // TODO renvoi du résultat retourné par le joinpoint
    ``` 
    Relancer le test unitaire pour vérifier la bonne application de l'aspect et la non regression.

12. Réfléchir à la possibilité d'activer ou désactiver au runtime le log du temps d'exécution en fonction de la valeur d'un champ booléen (que nous pouvons appeler `enabled`).
   Comment agir sur la valeur de ce champ alors que l'application est déployée ?