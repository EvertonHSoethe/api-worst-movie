package com.outsera.api_worst_movie.service;

import com.outsera.api_worst_movie.dto.ProducerAwardIntervalsResponseDTO;
import com.outsera.api_worst_movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerAwardIntervalsService {

    private final MovieRepository movieRepository;

    public ProducerAwardIntervalsResponseDTO getProducerAwardIntervals() {
        var movieList = movieRepository.findAll();


        return ProducerAwardIntervalsResponseDTO.builder().build();
    }
}
