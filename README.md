# 8INF853 - SpringBoot

## Installation

* Cloner le dépôt https://github.com/LouisBertin/8INF853_SpringBoot sur sa machine locale
* Dans Intellij choisir **Import project** > **Import Project from external model** > **Maven** > Ensuite toujours choisir **Next**
<br> :warning:️ à l’étape de sélection du SDK : choisir la plus récente disponible sur votre machine (version conseillée SDK>=8)

* Grâce à Maven, Intellij va setup automatiquement la configuration pour **run** SpringBoot
* Ensuite dans Intellij, sélectionner **Run** > **Run**
* SpringBoot est accessible à l’adresse http://localhost:8080

## Base de données

La base de données fonctionne avec Docker : MySQL 5.7.25 - PhpMyAdmin 4.8.5. <br />
Cela permet aux collaborateurs d'avoir le même environnement de travail : plus simple à améliorer, évite de nombreux bugs, gains de productivité.

### Installation
**Important** : pour configurer la base de données, dupliquer le fichier `./src/main/resources/application.properties.dist` et renommer le `application.properties`, ensuite renseigner les informations de votre MySQL

> Avec Docker
* Installer [Docker - macOS](https://runnable.com/docker/install-docker-on-macos), [Docker - Windows](https://runnable.com/docker/install-docker-on-windows-10), [Docker - Linux](https://runnable.com/docker/install-docker-on-linux)
* Se placer dans le répertoire `./db` du project
* Exécuter la commande `docker-compose up -d` : PhpMyAdmin est disponible à l'adresse http://localhost:8181 <br />
**ID** : root, **PWD** : struts
* Pour stopper l'exécution, utiliser la commande `docker-compose down`

> Sans Docker

Utiliser Laragon, Wamp, Mamp ou Linux Subsystem sur Windows, créer la base `struts`
Vous pouvez utiliser le fichier `./db/mysql_init/schema.sql` pour hydrater votre bdd

## Stack technique

* Spring Boot - 2.1.2
* Tomcat - 9.0.14
* Hibernate Core - 5.3.7
* Bootstrap - 4.3.1
* Thymeleaf - 2.1.2
* Thymeleaf Layout Dialect - 2.3.0
* Sass maven plugin - 3.7.1