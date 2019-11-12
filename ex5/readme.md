# Exercice 5 (Websocket, échange de messages) :

1. Examiner la dépendances vers `spring-boot-starter-websocket`, puis les dépendances transitives.

2. Lancer la méthode `main` dans `com.acme.ex5.MyApplication`

3. Examiner le code javascript du fichier `src/main/resources/static/index.html`

3. Ouvrir http://localhost:8080 depuis un navigateur. Poster un message sur le chat.

4. Décommenter les lignes 52 à 72 dans la classe `MyApplication`.

5. Dans index.html : changer les souscriptions et les publications : 
   * la souscription doit porter sur chats/123/accepted-messages   
   * la publication doit porter sur chats/123/users-messages

6. Relancer l'application puis recharger la page, observer la différence. Désormais le programme Java est 'intercalé' entre les émeteurs et les récepteurs de message
