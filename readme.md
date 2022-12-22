# Système de gestion de base de données 

## Auteur 
- Cedric Andriambelo
+ ETU1381

## Présentation du projet
Systeme de gestion de base de données en réseau

## Langage
> Java

## Structure du code
##### Classes
1. package traitement
    - Checkeur : classe servant à verifier et à definir la fonction à executer pour traiter la requete envoyée par le client ou les erreurs eventuelles (par exemple : si la requete contient le mot-clé `insert`)
    - Database : classe contenant toutes les fonctions de traitement correspondantes aux actions courants de gestion de base de données (create, use, insert, select, update, delete).
        > C'est dans cette classe que sont definies les operations de manipulations de fichiers
        > Cette classe n'est utilisée que côté serveur
2. package modele
    - Client : classe représentant le comportement de l'application côté client
        - Classe d'execution (principalle)
        - Implemente l'API Socket pour pouvoir communiquer en réseau avec l'application serveur
        - Nécessité de définir le numero de port dans la fonction `main()`
        - L'adresse ip du serveur cible se définit lors de l'execution dans la console
        - Possède deux threads séparés pour l'envoi de requête et la réception de réponse
        > Chaque Thread est demarré lors de l'execution de lq classe pricipalle et non dans le constructeur
        - La methode `run()` du thread d'envoi lance une boucle infinie qui scanne en permanence les entrees du client
        NB : le client peut aussi bien utiliser l'interface graphique que la console pour envvoyer ses requêtes
        - La methode `run()` du thread de reception lance une boucle infinie tant que le serveur continue à envoyer des reponses et affiche ces réponses
    - Server : classe représentant le comportement de l'application coté serveur
        - Classe d'execution (principalle)
        - Implemente l'API Socket et ServerSocket pour pouvoir communiquer avec l'application client
        N.B : seule le serveur doit implementer l'API ServerSocket pour pouvoir accepter les connexions entrantes
        - Nécessité de définir le numero de port dans la fonction `main`
        - Ne possède qu'un seul thread qui assure la reception de la requete et l'envoi de reponse (pour que chaque client ait droit a un thread)
        - La fonction `run()` du thread lance une boucle infinie tant que la connexion est maintenue et qu'une requete est envoyée