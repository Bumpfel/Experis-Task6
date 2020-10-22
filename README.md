# Experis-Task6
This project is an imdb actor API made in Spring, PostgreSQL, 

# Deployed URL
https://experis-task6.herokuapp.com/

# API calls
* GET /api/actors - get all actors
* GET /api/actors/:id - get specific actor
* POST /api/actors  - Add an actor<br>
Body: String firstName, String lastName, String dateOfBirth, String imdbUrl
* PUT /api/actors/:id - Replace an actor
Body: String firstName, String lastName, String dateOfBirth, String imdbUrl
* PATCH /api/actors/:id - Modify parts of an actor
Body: String firstName?, String lastName?, String dateOfBirth?, String imdbUrl?
* DELETE /api/actors/:id - Delete an actor

# Requirements to run
* Java SDK 14+
* Spring
* PostgreSQL

# How to run
* Run main method in src/main/java/se/experis/Task6HerokunateApplication.java
* Set up a [https://www.postgresql.org/](postgreSQL server) on port 5432
