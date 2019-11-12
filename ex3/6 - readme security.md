# Exercice 3 (partie 6 : sécurisation d'une API REST avec Open ID Connect et Spring security) :

## Configuration du serveur Open Id Connect

1. Dans la console d'administration Keycloak (http://localhost:8282, admin/admin), créer un utilisateur :
	
	* username : jdoe
	* mot de passe (_credentials_) : azerty.

## Activation de Spring Security

2. Ajouter une dépendance vers `spring-boot-starter-security` et :

	```xml
	<dependency>
		<groupId>org.springframework.security</groupId>
		<artifactId>spring-security-oauth2-resource-server</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.security</groupId>
		<artifactId>spring-security-oauth2-jose</artifactId>
	</dependency>
	```
	Remarquer l'activation automatique de la sécurité sur l'application.

3. Ajouter la propriété suivante au fichier `application.properties` : 

	```
	spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8282/auth/realms/my-realm
	```
	
	Ainsi Spring security saura obtenir auprès du serveur Open ID Connect la clé publique permettant de vérifier les _tokens_ figurant dans l'en-tête `Authorization` des requêtes entrantes.

4. Ajouter la classe de configuration suivante dans `com.acme.ex3.ApplicationConfig` : 

	```java
	@EnableWebSecurity 
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	public static class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	}
	```
	
	et, dans cette classe : 
	
	* désactiver la protection CSRF.
	* activer la reconnaissance des tokens JWT.
	* autoriser toutes les requêtes entrantes, que l'appelant soit authentifié ou non.

5. Reprendre la méthode permettant de réserver un livre :

	* l'invocation de la méthode ne doit être possible que si l'utilisateur est authentifié.
	* la propriété `username` de la commande doit être le nom de l'utilisateur en cours.