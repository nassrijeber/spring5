# Exercice 3 (partie 2 : gestion des transactions) :

**Objectifs** : 
* utilisation des _repositories_ Spring Data
* délégation à Spring de la gestion des transactions
* utilisation de l'annotation `@Sql`
* introduction aux invocation asynchrones

*****

1. Annoter les commandes avec l'annotation `Usecase` pour préciser par quel(s) `CommandHandler` elles ont vocation à être traitées : 
   * les commandes de type `MemberRegistrationCommand` doivent être traitées par le _bean_  de type `MemberRegistrationCommandHandler`
   * les commandes de type `ReservationCommand` doivent être traitées par le _bean_  de type `ReservationCommandHandler`

2. Compléter la classe `com.acme.common.service.impl.CommandProcessorImpl` :
   * suivre les TODO
   * une transaction doit entourer l'invocation de la méthode `process`
   * le `CommandProcessorImpl` doit être présent dans le contexte applicatif.

3. Compléter les implémentations de `CommandHandler` (package `com.acme.ex3.business.impl`) :
   * afin qu'ils soient présents dans le contexte applicatif.
   * afin d'interdir l'invocation de la méthode `handle` si une transaction n'est pas déjà en cours.
   * suivre les TODO dans 
   		* `com.acme.ex3.business.impl.MemberRegistrationCommandHandler`
   		* `com.acme.ex3.business.impl.ReservationCommandHandler`

4. Lancer les tests :

	* `com.acme.ex3.service.command.MemberRegistrationCommandTest`.
	* `com.acme.ex3.service.command.ReservationCommandTest`.