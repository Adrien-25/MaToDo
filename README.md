# To-Do List Application

## Description

**MaToDo** est une application web intuitive pour la gestion des tâches. Elle permet de créer, lire, modifier et supprimer des tâches facilement. Développée avec **Spring Boot** et **Gradle**, cette application met l'accent sur la simplicité d'utilisation et la fiabilité.

## Fonctionnalités

- **Gestion des tâches (CRUD)** :
  - Ajouter de nouvelles tâches.
  - Afficher la liste des tâches existantes.
  - Mettre à jour les tâches.
  - Supprimer les tâches terminées ou non souhaitées.
- **Authentification utilisateur** :
  - Enregistrement et connexion sécurisés des utilisateurs.
  - Gestion des tâches spécifiques à chaque utilisateur.
- **Interface utilisateur ergonomique** : Conçue avec **Thymeleaf** pour une navigation fluide.
- **Base de données robuste** : Stockage sécurisé des tâches dans une base de données **PostgreSQL**.
- **Qualité logicielle** :
  - Tests unitaires intégrés pour assurer la fiabilité des fonctionnalités.
  - Suivi des bonnes pratiques de développement.

## Aperçu des technologies

| Technologie     | Description                                                |
| --------------- | ---------------------------------------------------------- |
| **Spring Boot** | Framework Java pour le développement d'applications web.   |
| **Gradle**      | Outil de gestion de projet et de construction automatisée. |
| **PostgreSQL**  | Système de gestion de bases de données relationnelles.     |
| **Thymeleaf**   | Moteur de template pour générer des pages web dynamiques.  |

## Prérequis

Avant d'installer l'application, assurez-vous d'avoir :

1. [Java JDK 23+](https://www.oracle.com/java/technologies/javase-jdk23-downloads.html) installé.
2. [Gradle](https://gradle.org/install/) (ou utilisez le **Gradle Wrapper** inclus dans le projet).
3. [PostgreSQL](https://www.postgresql.org/download/) configuré avec un utilisateur et une base de données.

## Installation

### 1. Cloner le dépôt

```bash
git clone https://github.com/Adrien-25/MaToDo.git
cd todo-list-app
```

### 2. Configurer PostgreSQL avec des variables d'environnement  

Pour configurer votre base de données, définissez les variables d'environnement suivantes dans votre système :  

- **DB_URL** : L'URL de votre base de données PostgreSQL, par exemple :  
  `jdbc:postgresql://localhost:5432/votre_base_de_donnees`  
- **DB_USERNAME** : Le nom d'utilisateur de votre base de données.  
- **DB_PASSWORD** : Le mot de passe associé à votre utilisateur.  

