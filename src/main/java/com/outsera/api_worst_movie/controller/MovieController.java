package com.outsera.api_worst_movie.controller;

import com.outsera.api_worst_movie.dto.MovieRequestDTO;
import com.outsera.api_worst_movie.dto.MovieResponseDTO;
import com.outsera.api_worst_movie.mapper.MovieMapper;
import com.outsera.api_worst_movie.service.MovieService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/movies")
public class MovieController {

    public final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @PostMapping
    public MovieResponseDTO create(@RequestBody MovieRequestDTO movieRequestDTO) {
        return MovieMapper.toResponseDTO(movieService.save(movieRequestDTO));
    }
}
