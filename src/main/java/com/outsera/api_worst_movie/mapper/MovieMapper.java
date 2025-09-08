package com.outsera.api_worst_movie.mapper;

import com.outsera.api_worst_movie.dto.MovieRequestDTO;
import com.outsera.api_worst_movie.dto.MovieResponseDTO;
import com.outsera.api_worst_movie.model.Movie;


public class MovieMapper {

    public static Movie toEntity(MovieRequestDTO movieRequestDTO) {
        return Movie.builder()
                .title(movieRequestDTO.getTitle())
                .producer(movieRequestDTO.getProducer())
                .studio(movieRequestDTO.getStudio())
                .worstMovieWinner(movieRequestDTO.isWorstMovieWinner())
                .releaseYear(movieRequestDTO.getReleaseYear())
                .build();
    }

    public static MovieResponseDTO toResponseDTO(Movie movie) {
        return MovieResponseDTO.builder()
                .releaseYear(movie.getReleaseYear())
                .title(movie.getTitle())
                .studio(movie.getStudio())
                .producer(movie.getProducer())
                .worstMovieWinner(movie.isWorstMovieWinner())
                .build();
    }
}
