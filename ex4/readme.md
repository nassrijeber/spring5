#  Exercice 4 (Spring Data R2DBC) :

Objectifs : 
- Déclaration d'un _driver_ réactif pour PostgreSQL.
- Compréhension des principes de la programmation réactive.
- Utilisation de l'interface `ReactiveCrudRepository`.
- Développement d'une API REST réactive.
*****

1. Observer le pom.xml, et notamment les dépendances de `spring-starter-webflux` : plus de _servlet container_ mais un serveur embarqué Netty.

2. Observer la classe `BookRepository`, et notamment les méthodes héritées (voir le code source de `R2dbcRepository` et donc de `ReactiveCrudRepository`. Les méthodes retournent des `Flux<T>` et des `Mono<T>`. L'objectif est de pouvoir requéter une base de données et d'obtenir *immédiatement* (donc de manière non bloquante) un objet (`Flux` ou `Mono`) sur lequel nous pouvons déclarer les traitements à réaliser sur les éléments lorsqu'ils seront disponibles.

3. Dans la classe de configuration : compte tenu de la jeunesse de R2DBC, nous devons déclarer les méthodes productrices fournissant la connectivité réactive à la base PostgreSQL. Un _starter_ `spring-boot-starter-data-r2dbc` sera bientôt proposé.

4. Observer le code de la classe `com.acme.ex4.controller.BookController`, remarquer que nos méthodes :
	* invoquent les méthodes du _repository_
	* obtiennent en retour des `Mono` et des `Flux`
	* retourne à leur tour des `Mono` et des `Flux`
	
	C'est le serveur web qui souscrira aux `Mono` et aux `Flux` (appel à la méthode `subscribe`) , c'est seulement alors que la base de données sera requétée.
	
	Pour bien comprendre ce nouveau paradigme : lire et exécuter les tests de la classe `BookRepositoryTest`.

4. A lire : 

	* Panorama sur la Programmation réactive : http://www.dng-consulting.com/?p=892
	* Reactive Web Applications with Spring 5 by Rossen Stoyanchev : https://www.youtube.com/watch?v=rdgJ8fOxJhc
	*  Reactor by example : https://www.infoq.com/articles/reactor-by-example
	* Comparing Java 8, RxJava, Reactor : http://alexsderkach.io/comparing-java-8-rxjava-reactor/
	* Reactive SQL Data Access with R2DBC : https://spring.io/blog/2018/09/27/the-reactive-revolution-at-springone-platform-2018-part-1-n#reactive-sql-data-access-with-r2dbc
