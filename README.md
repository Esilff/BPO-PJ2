## À la personne qui consulte ça

**Default copyright laws applies. All right reserved.**

Notre code est certes visible publiquement, mais cela ne signifie pas forcément "open source".  
La non présence de fichier LICENSE rends ce code soumis aux règles de GitHub : la loi relative au copyright s'applique systématiquement.
Vous ne pouvez reproduire ou réutiliser le code présent dans ce dépôt. 

Copier (ou le simple fait de lire notre code) c'est spoiler le travail de logique qu'on vous demande de fournir, et c'est aussi contre-productif.  
Si vous en arrivez-là, c'est que vous avez forcément besoin d'aide. 
Préférez donc vous adressez directement sur discord, il y a toutes les chances qu'une personne vous réponde.

# BPO-PJ2

Projet de création d'un jeu d'échecs en CLI sur Java. 

Ce projet fonctionne sous IntelliJ Idea et contient les fichiers de projets. Ce projet devrait fonctionner sur Eclipse.  
Peu importe l'IDE, il est très probable qu'une config supplémentaire soit nécessaire afin de pouvoir exécuter le code.

Ce projet possède un également un support maven (principalement pour de l'intégration continue) :
* `mvn test` pour exécuter les tests unitaires
* `mvn package` pour tester, compiler, et packager le projet
* `mvn javadoc:javadoc` pour compiler la javadoc en fichiers html
