package com.outsera.api_worst_movie.mapper;

import com.outsera.api_worst_movie.dto.MovieRequestDTO;
import com.outsera.api_worst_movie.dto.MovieResponseDTO;
import com.outsera.api_worst_movie.model.Movie;


public class MovieMapper {

    public static Movie toEntity(MovieRequestDTO movieRequestDTO) {
        return Movie.builder()
                .title(movieRequestDTO.getTitle())
                .producers(movieRequestDTO.getProducers())
                .studios(movieRequestDTO.getStudios())
                .winner(movieRequestDTO.isWinner())
                .releaseYear(movieRequestDTO.getReleaseYear())
                .build();
    }

    public static MovieResponseDTO toResponseDTO(Movie movie) {
        return MovieResponseDTO.builder()
                .releaseYear(movie.getReleaseYear())
                .title(movie.getTitle())
                .studios(movie.getStudios())
                .producers(movie.getProducers())
                .winner(movie.isWinner())
                .build();
    }
}
