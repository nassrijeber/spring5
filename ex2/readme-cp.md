Chaque commande correspond à un cas d'utilisation et contient : 
* des champs utilisés par l'appelant (disons l'IHM).
* des champs utilisées par l'application pour fournir les résultats attendus par l'appelannt.
* éventuellement : des routines de validation avant traitement/aprés traitement, car la commande est la mieux placée pour savoir si elle est en état d'être traitée ou non.

Ainsi la commande est une structure d'échange entre l'IHM et l'application, il est possible d'ajouter de nouvelles propriétés
sans rien changer à la manière d'échanger les messages, c'est à dire sans breaking changes => favorise l'évolutivité et l'acceptation de nouvelles demandes de la part de la MOA.

Dans `AbstractCommand`, les méthodes `validateStateBeforeHandling` et `validateStateAfterHandling` permettent de vérifier les pré conditions et les post conditions.

une fois la commande reçue par le `CommandProcessor` (méthode `process`), celui-ci 

* déclenche la validation d'état avant traitement (`validateStateBeforeHandling()`)
* publie la commande en tant qu'évènement. Celle-ci est alors transmise aux handlers capables de la traiter : c'est à dire aux méthode annotées par `@EventListener` et acceptant en paramètre une commande du type de celle publiée.
* déclenche la validation d'état avant traitement (`validateStateAfterHandling()`) 

A propos de la gestion des exceptions

* les exceptions applicatives sont des `CommandException`
* les exceptions techniques sont les autres exceptions.

Cette séparation claire permet un traitement différencié :

* pour les `CommandException` : minimum de log et présentation du message à l'utilisateur
* pour les autres exceptions : maximum de long et présentation d'un message générique à l'utilisateur.

Au runtime, le `CommandProcessor` orchestre le traitement des commandes mais en ignorant tout des commandes concrètes ou des handlers concrets :
il est agnostique et c'est pour cette raison qu'il est dans "common". Il est possible d'abonner / désabonner des handlers à une commande, 
encore une fois sans _breaking changes_. => favorise l'évolutivité et l'adaptation de l'application à de nouveaux besoins.
