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
## Tests
* mvn -Dtest=ProducerAwardIntervalsControllerIT test

## The application will start on:
* http://localhost:8080
### If you want to see the register's on database
* http://localhost:8080/h2-console
* **jdbc-url:** jdbc:h2:mem:api_worst_movie
* **command:** SELECT * FROM MOVIE

## Endpoints disponíveis:

### GET `/v1/producers/award-intervals`

Returns the producer with the longest and shortest intervals between two consecutive awards.

**Request:**

`curl --location 'http://localhost:8080/v1/producers/award-intervals'`

**Return:**

`CSV imported successfully!`


### POST `/v1/movies`

Creates a new film record in the database.

**Request:**

`curl --location 'http://localhost:8080/v1/movies' \
--header 'Content-Type: application/json' \
--data '{
"releaseYear": 1992,
"title": "Teste Movie Everton",
"studios": "Disney",
"producers": "Joel Silver",
"winner": true
}'`

**Return:**

`{
"id": 208,
"releaseYear": 1992,
"title": "Teste Movie Everton",
"studios": "Disney",
"producers": "Joel Silver",
"winner": true
}`

### POST /v1/internal/movies/upload-csv

Imports a CSV file into the database.

**Request:**

`curl --location 'http://localhost:8080/v1/internal/movies/upload-csv' \
  --form 'file=@"/C:/Users/Everton/Downloads/Movielist.csv"'`

**Return:**

`{
"min": [
{
"producers": "Joel Silver",
"interval": 1,
"previousWin": 1990,
"followingWin": 1991
},
{
"producers": "Joel Silver",
"interval": 1,
"previousWin": 1991,
"followingWin": 1992
}
],
"max": [
{
"producers": "Matthew Vaughn",
"interval": 13,
"previousWin": 2002,
"followingWin": 2015
}
]
}`