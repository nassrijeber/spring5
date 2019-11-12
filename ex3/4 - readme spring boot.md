# Exercice 3 (partie 4 : utilisation de Spring boot) :

**Objectifs** : 
* compréhension des dépendances d'un projet Spring boot.
* écriture du fichier application.properties.
* activation de la configuration automatique.
* tests avec Spring boot.
* utilisation du serveur web embarqué.
* _packaging_ d'une application Spring boot.

*****

## La gestion des dépendances

1. Remplacer le BOM `spring-framework-bom` par celui de Spring boot, constatez que celui-ci ne s'applique pas  seulement aux dépendances du groupId `org.springframework`.

2. Ajouter les dépendances vers les _starters_ Spring boot : 

	* `spring-boot-starter-data-jpa` pour l'accès aux données via JPA
	* `spring-boot-starter-test` pour les tests.
	
	Supprimer les dépendances devenues redondantes.
	
## La configuration automatique

3. Reprendre les noms des propriétés du fichier `application.properties` afin qu'ils correspondent à ceux attendus par spring boot. Il suffit de les préfixer par `spring`.

4. Activer la configuration automatique sur la classe `ApplicationConfig` puis
	
	* supprimer les méthodes productrices correspondant à des _beans_ que Spring créera automatiquement.
	* considérer la possibilité d'utiliser l'annotation `@SpringBootApplication`

Relancer les tests.

Constater le problème lors de la mise en place de l'`EntityManagerFactory`. Cela vient de la stratégie de nommage JPA (_naming strategy_) choisie par  Spring. Pour le résoudre, ajouter les deux propriétés suivantes au fichier `application.properties` :

```
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl
```

Explications (https://docs.spring.io/spring-boot/docs/current/reference/html/howto-data-access.html#howto-configure-hibernate-naming-strategy) : _By default, Spring Boot configures the physical naming strategy with SpringPhysicalNamingStrategy. This implementation provides the same table structure as Hibernate 4: all dots are replaced by underscores and camel casing is replaced by underscores as well. By default, all table names are generated in lower case, but it is possible to override that flag if your schema requires it._

Constater (via la méthode de test `testFind`) que le cache niveau 2 n'est pas actif par défaut. Pour l'activer, et ainsi maitriser la manière dont Spring créé le _bean_ `EntityManagerFactory` : ajouter la propriété suivante dans le fichier `application.properties` : 

```
spring.jpa.properties.javax.persistence.sharedCache.mode=ENABLE_SELECTIVE
```

## Les tests

5. Modifier les tests : remplacer `@SpringJunitConfig` (ou les deux annotations `@ExtendWith` et `@ContextConfiguration`) par `@SpringBootTest`.

6. Dans `com.acme.ex3.repository.BookRepositoryTest` : référencer le fichier `application-for-tests.properties` afin de déléguer à Spring la création d'une `DataSource` et d'une `EntityManagerFactory` qui utilise une base Derby.

Relancer les tests.

## Le serveur web embarqué

7. Ajouter une dépendance vers `spring-boot-starter-web` afin que l'application puisse incorporer un serveur web et recevoir des requêtes HTTP.

8. Dans le corps de la méthode `main` de la classe `ApplicationConfig` :

	* remplacer `new AnnotationConfigApplicationContext(ApplicationConfig.class)` par `SpringApplication.run(ApplicationConfig.class, args)`. Il devient possible de supprimer l'annotation `@PropertySource` puisque le fichier `application.properties` sera prise en compte automatiquement au démarrage de l'application.
	* supprimer l'appel à la méthode `close`.

	Lancer l'application.

9. Ajouter une dépendance vers `spring-boot-devtools` pour bénéficier du rechargement à chaud :
	```xml
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-devtools</artifactId>
	</dependency>
	```
	
## Le packaging

10. Ajouter le _plugin_ `spring-boot-maven-plugin` afin que la phase _package_ du _build_ Maven conduise à obtenir un _fat jar_ puis exécuter `mvn package`

*****

**Depuis l'explorateur** :

* copier/coller le dossier `com` (`ex3/src/main/java`) vers le dossier `ex3a/src/main/java`.
* copier/coller les dossiers `service` et `repository` (`ex3/src/test/java/com/acme/ex3`) vers le dossier `ex3a/src/test/java/com/acme/ex3`.