# api-worst-movie
Project to test my java knowlodges


## 🚀 Technologies

* Java 21

* Spring Boot

* Spring Web

* Spring Data JPA

* H2 Database

* Springdoc OpenAPI (Swagger)

* Maven

## ▶️ Running the Project
* mvn spring-boot:run 

## The application will start on:
* http://localhost:8080

## Endpoints disponíveis:
`GET /v1/producers/award-intervals`
* Retorna o produtor com o maior e menor intervalo entre dois prêmios consecutivos.

`POST /v1/movies`
* Cria um novo registro de filme na base de dados

`POST /v1/internal/movies/upload-csv`
* Importa um arquivo csv na base de dados