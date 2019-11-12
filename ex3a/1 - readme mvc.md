#  Exercice 3a (IHM avec Spring MVC) :

Objectifs : 
- mise en oeuvre de spring mvc pour concevoir une IHM
- utilisation de vues vues JSP
- couplage avec bean-validation

*****

1. Créer un controleur `com.acme.ex3.web.controller.BookController`, ajouter une méthode afin qu'un `GET` sur _/books_ déclenche le rendu de la vue `/books/list.jsp`.

2. Dans `com.acme.ex3.web.controller.BookController`, ajouter une méthode capable de recevoir à la soumission du formulaire (le formulaire est soumis en **POST** sur _books_).

	DON'T : récupérér une à une les valeurs saisies dans le formulaire avec des paramètres annotés par `@RequestParam`
	
	DO : récupérer les valeurs saisies dans le formulaire dans un objet reconstruit par Spring.

	Cette méthode doit déclencher le rendue de la vue `/books/list.jsp` mais avec cette fois un attribut `results` dans le modèle.
	
	`results` correspond à la liste des livres obtenue auprès du _repository_ `BookRepository`. 

3. Constater la perte de la saisie utilisateur lorsque l'on soumet le formulaire, considérer la possibilité d'utiliser de lier chaque champ de saisie à une propriété du modèle.

4. Constater le problème lorsque l'on accède à nouveau en **GET** à _/books_, le résoudre.

5. Appliquer la validation de saisie sur le formulaire de recherche, faire apparaître le message d'erreur à côté du champ de saisie dans la vue.

6. Dans la classe `BookController`, ajouter une méthode afin qu'un `GET` sur _/books/{id}_ déclenche le rendu de la vue `/books/detail.jsp`. Cette méthode doit permettre de transmettre à la vue le livre {id} sous le nom `entity`. `entity` correspond au livre numéro _id_ obtenue auprès du _repository_ `BookRepository`.
    
10. Créer dans `BookController` la méthode permettant de répondre à une soumission du formulaire de réservation d'un livre. Cette méthode doit rediriger vers la liste de résultats. Avant d'appeler la méthode `process` : vérifier la bonne récupération de la saisie utilisateur et la redirection.

11. Appliquer la validation de saisie le formulaire de réservation, faire apparaître le message d'erreur à côté du champ de saisie dans la vue.

12. Créer un _controller advice_ `com.acme.ex3.web.controller.MyControllerAdvice`, reprendre et compléter le code suivant :

	```java
	public String onCommandException(Model model, Exception e) {
		model.addAttribute("exception", e);
		return null;
	}

	public String onOtherException() {
		return null;
	}
	```

	* les exceptions métiers (`CommandException`) doivent être associées à la vue la vue `_errors/command-exception`
	* les autres exceptions doivent être associées à la vue la vue `_errors/other-exception.html`s exceptions (`Throwable`) à la vue `_errors/other-exception`