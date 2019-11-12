#  Exercice 3a (partie 2 : Spring security)

Objectif : mise en oeuvre de spring security sur la couche web et sur la couche applicative

***

1. Ajouter à `com.acme.ex3.ApplicationConfig` une classe statique de configuration dédiée à la sécurité. Celle-ci doit : 
    - étendre `WebSecurityConfigurerAdapter`
    - étre annotée par les bonnes annotations

2. Essayer d'accéder à la route `/books`, observer la réaction du filtre.

3. Dans la classe de configuration créer en 1, redéfinir la méthode 
    ```java
    void configure(AuthenticationManagerBuilder auth)
    ```
    Le référentiel utilisateur est dans la base de données ex3. Début du code d'implémentation : 

    ```java
    DataSource ds = new DriverManagerDataSource("jdbc:postgresql://localhost:5432/ex3", "postgres", null);
	String usersByUsernameQuery = "select username, password, true from Member where username=?";
	String authoritiesByUsernameQuery = "select username, authority from authorities where username=?";
	PasswordEncoder encoder = new BCryptPasswordEncoder();
	```
		
	Essayer ensuite de s'authentifier avec le nom d'utilisateur _jdoe_ et le mot de passe _azerty_

4. Rédéfinir la méthode `void configure(HttpSecurity http)` : 
	* seule l'authentifcation par formulaire de login est activée.
	* toutes les routes sont accessibles à tous les utilisateurs.
	

5. Utiliser la taglib `spring-security-taglibs` dans la vue `books/detail` :

	* si l'utilisateur a la permission _borrow-books_ : afficher le formulaire de réservation (supprimer alors le champ de saisie pour `username`).
	* si l'utilisateur n'est pas authentifié (`!isAuthenticated()`) : afficher un lien vers le formulaire de login (`/login`).

6. Reprendre la méthode permettant de réserver un livre :

	* l'invocation de la méthode ne doit être possible que si l'utilisateur a la permission `borrow-books`.
	* ajouter un paramètre pour connaitre le contexte d'authentification de l'utilisateur en cours puis valoriser la propriété `username` de la commande avec cet objet.


7. Remarquer qu'une authentification réussie aurait avantage à redirigier l'utilisateur vers la fiche détail du livre. Quelques pistes :
		
	* utilisater un formulaire de login qui conserve dans un champ caché l'information sur l'url de la page suivante en cas d'authentification réussie. Cette information peut être lue :
		* dans l'en-tête `referer`
		* dans un paramètre présent dans le lien vers le formulaire de login (?redirectUrl=...).
	* l'inscription d'un `AuthenticationSuccessHandler` dans la configuration Spring (éventuellement sous forme d'expression lambda), qui accède à cette information et redirigerait l'utilisateur vers la bonne URL.

	Mais attention aux attaques _open redirect_ : https://github.com/OWASP/CheatSheetSeries/blob/master/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.md
