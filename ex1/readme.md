# Exercice 1 :

Objectifs : 
- compréhension du pom.xml
- détection des dépendances
- préparation des injections
- déclaration des beans auprès de Spring

*****

1. Ecrire deux implémentations de l'interface `MovieDao` : 
	* `com.acme.ex1.dao.impl.FoxMovieDaoImpl`
	* `com.acme.ex1.dao.impl.WarnerMovieDaoImpl`

	chacune renvoie un `Stream<Movie>` filtré d'après le paramètre `title` de la méthode `retrieve`. 
	
	A propos des streams : https://stackoverflow.com/a/24679745

2. Ecrire une implémentation `MovieServiceImpl` de `MovieService`, la méthode `find` utilise une des implémentations de `MovieDao` pour renvoyer un `Stream` de `Movie`
	* Comprendre la dépendance entre les implémentations de `MovieService` et les implémentations de `MovieDao`
	* Rendre possible l'injection d'une `MovieDao` dans la classe `MovieServiceImpl`

3. Ecrire une deuxième implémentation `SuperMovieServiceImpl` de l'interface `MovieService`. Cette deuxième implémentation doit être capable de recevoir plusieurs `MovieDao` et non plus une seule

4. (Créer une classe `com.acme.ex1.ApplicationFactory` chargée de gérer les composants de l'application, c'est à dire leur cycle de vie et les injections les uns dans les autres).

5. Créer une classe de configuration `ApplicationConfig` dans le package `com.acme.ex1`

6. Confier à Spring la gestion de nos composants : Spring va désormais jouer le rôle de *factory*, `ApplicationFactory` ne sera plus nécessaire.

7. Compléter et lancer les tests unitaires

	* `SuperMovieServiceImplTest`
	* `MovieServiceImplTest`.

8. Comprendre le problème qui se pose sur `MovieServiceImpl`, le résoudre.