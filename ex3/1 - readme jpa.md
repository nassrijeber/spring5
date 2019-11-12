# Exercice 3 (partie 1 : couplage Spring - JPA, Spring Data)

**Objectifs** : 

* couplage Spring - JPA (Java persistence API)
* utilisation de Spring Data

*****

## configuration

Sur la classe `com.acme.ex3.ApplicationConfig`

1. Référencer le fichier `application.properties`

2. Compléter la méthode productrice `ds`. 

3. Compléter la méthode productrice `entityManagerFactory`.

4. Ajouter une méthode productrice retournant un `PlaformTransactionManager` (implémentation : `JpaTransactionManager`)

5. Activer la reconnaissance des annotations `@Transactional`


## surcouche spring data

1. Ajouter la dépendance à `spring-data-jpa` : 

	```xml
	<dependency>
		<groupId>org.springframework.data</groupId>
		<artifactId>spring-data-jpa</artifactId>
		<version>2.2.0.RELEASE</version>
	</dependency>
	```

2. Ajouter 4 _repositories_ dans le package `com.acme.ex3.repository` : 

	* `AuthorRepository`
	* `ReservationRepository`
	* `BookRepository`
	* `MemberRepository`
		
	Pour chacun le type de l'`ID` est `Integer`

	Définir dans `MemberRepository` une méthode permettant de rechercher un _member_ par son nom d'utilisateur (rappel : un _member_ possède un _acccount_ qui lui même possède une propriété _username_)
	
3. Activer sur la classe de configuration `ApplicationConfig` la prise en charge de _repositories_ JPA

4. Décommenter les tests unitaires de la classe `com.acme.ex3.repository.BookRepositoryTest` (puis organiser les imports) et coupler Spring et Junit.

5. Lancer les tests unitaires (un par un pour bien observer les logs).
