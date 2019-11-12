# Exercice 3 (partie 5 : REST avec Spring MVC) :

Objectifs : 
- mise en oeuvre de spring mvc pour concevoir une API REST
- couplage avec bean-validation

*****

1. Examiner les dépendances transitives de `spring-boot-starter-web` dans le pom.xml, notamment :
	* `spring-webmvc` 
	* `jackson-databind` pour la sérialisation et déserialisation en json.
	* `tomcat-embed-core` en tant que serveur web.

2. Créer une classe `com.acme.ex3.web.endpoint.BookEndpoint` et ajouter une méthode capable de recevoir un **GET** sur http://localhost:8080/books et de renvoyer une liste de représentations simplifiées de livres (`List<BookDto>`). Cette liste sera construite à partir d'une `List<Book>` obtenue auprès du `BookRepository`
    
    A propos des DTO : https://stackoverflow.com/a/36175349

5. Ajouter une méthode permettant de recevoir l'expression de la recherches dans une instance de la classe `Book` (_query by example_). Attention : l'URI (c'est à dire le _path_) doit être un nom de ressource.

6. Ajouter à la classe `com.acme.ex3.web.endpoint.BookEndpoint` une méthode capable de recevoir un **GET** sur http://localhost:8080//books/{id}. Une telle requête doit permettre d'obtenir la représentation simplifiée (classe `BookDto`) du livre numéro {id} obtenue auprès du _repository_ `BookRepository`. Attention, s'il n'y a pas de livre numéro {id} il ne faut pas renvoyer une réponse avec un _body_ vide et un statut 200... Début de l'implémentation :

7. Reprendre la classe `Book` et poser une contrainte de validation sur le champ `title` : la valeur doit être non nulle et non vide. Annoter ensuite les paramètres de type `Book` dans le `@RestController` afin que Spring retourne une erreur 400 si le `Book` n'est pas valide.

8. Ajouter dans le `_ControllerAdvice` deux gestionnaires d'exception (un pour les exception de type `BindException`, l'autre pour les exceptions de type `MethodArgumentNotValidException`) afin de controler le _body_ de la réponse 400 à retourner en cas d'erreur de validation (utiliser pour cela les deux premières méthodes de l'advice montré dans le support de cours).

9. Ajouter un _bean_  classe `com.acme.ex3.web.endpoint.ReservationEndpoint`, y déclarer une méthode permettant de réserver un livre.

10. Ajouter un _bean_ `com.acme.ex3.web.endpoint.MemberEndpoint`, y déclarer une méthode permettant de s'incrire comme nouveau membre.