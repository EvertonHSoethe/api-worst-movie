# api-worst-movie
Project to test my java knowlodges


## 🚀 Technologies

* Java 21

* Spring Boot

* Spring Web

* Spring Data JPA

* H2 Database

* Maven

## ▶️ Running the Project
* mvn spring-boot:run 

## The application will start on:
* http://localhost:8080
### If you want to see the register's on database
* http://localhost:8080/h2-console
* **jdbc-url:** jdbc:h2:mem:base_project
* **command:** SELECT * FROM MOVIE

## Endpoints disponíveis:
`GET /v1/producers/award-intervals`
* Returns the producer with the longest and shortest intervals between two consecutive awards.

`POST /v1/movies`
* Creates a new film record in the database.

`POST /v1/internal/movies/upload-csv`
* Imports a CSV file into the database.