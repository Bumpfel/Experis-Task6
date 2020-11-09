# Experis-Task6
This project is an actor REST API  with CRUD functionality made in Spring-Boot + Hibernate + Thymeleaf, and PostgreSQL. It also contains a very simple frontend built in vanilla JS that consumes the API and provides means to alter and view the content.

# Deployed URL
https://experis-task6-herokunate.herokuapp.com/

# API calls
* GET /api/actors - get all actors
* GET /api/actors/:id - get specific actor
* POST /api/actors - Add an actor<br>
* PUT /api/actors/:id - Replace an actor
* PATCH /api/actors/:id - Modify parts of an actor
* DELETE /api/actors/:id - Delete an actor

Actor body: String firstName, String lastName, String dateOfBirth, String imdbUrl

# Requirements to run project
* Java SDK 14+
* IDE that supports Maven & Spring-Boot
* PostgreSQL server
* Modern web browser

# How to run
* Run main method in src/main/java/se/experis/Task6HerokunateApplication.java
* Set up a [PostgreSQL server](https://www.postgresql.org) on port 5432 and create a database called "imdb"
