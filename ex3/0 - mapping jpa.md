# Exercice 3 (préalable) :

1. Examiner le fichier `pom.xml` et plus particulièrement les dépendances : 
   * `org.hibernate hibernate-core` (implémentation JPA)
   * `org.hibernate hibernate-ehcache` (cache de niveau 2 pour Hibernate)
   * `org.hibernate hibernate-hikaricp` (pool de connexion jdbc)
   * `org.hibernate hibernate-validator`
   * `org.springframework spring-orm` (couplage spring-jpa, spring-tx comme dépendance transitive)

2. Mapping (par annotations) pour les classes du package com.acme.ex3.model : 
   
   Comprendre pourquoi certaines classes sont des entités et d'autres des composants.
   
   Annoter chacune des classes avec l'annotation appropriée : `@Entity` ou `@Embeddable`.
   
   Mapper chaque champ de chaque classe avec l'annotation appropriée : 
   * `Category`
   * `Reservation`
   * `Member`(et donc `Account`)
   * `Book` (et donc `Comment`)
   * `Author`

   Rappel :
   * `@Id` ou `@Basic` si le type est simple (primitif, Date, String). `@Id` a réserver pour l'id, `@Basic` optionel car le champ est mappé par défaut.
   * `@ManyToOne` pour une relation de type association n-1 vers une `@Entity`
   * `@Embedded` pour une relation de type composition 1-1 vers un `@Embeddable`
   * `@ElementCollection` pour une relation de type composition 1-n vers un `@Embeddable`
   * `@OneToMany` pour une relation de type association 1-n vers une `@Entity`
   * `@ManyToMany` pour une relation de type association n-n vers une `@Entity`

   Plus de détails sur le fichier aide mémoire jpa.txt

3. Examiner le fichier `META-INF/persistence.xml`
