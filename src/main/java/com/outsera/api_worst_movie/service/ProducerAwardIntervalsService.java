package com.outsera.api_worst_movie.service;

import com.outsera.api_worst_movie.dto.ProducerAwardIntervalsResponseDTO;
import com.outsera.api_worst_movie.dto.ProducerAwardResponseDTO;
import com.outsera.api_worst_movie.model.Movie;
import com.outsera.api_worst_movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProducerAwardIntervalsService {

    private final MovieRepository movieRepository;

    public ProducerAwardIntervalsResponseDTO getProducerAwardIntervals() {
        var movieList = movieRepository.findAll();
        var mapOfWinners = transformWinnersToMap(movieList);
        return calculateAwardsInterval(mapOfWinners);
    }

    private Map<String, List<Long>> transformWinnersToMap(List<Movie> movieList) {
        Map<String, List<Long>> winnerProducers = new HashMap<>();

        for (Movie movie : movieList) {
            if (movie.isWinner()) {
                for (String producer : movie.getProducers().split(",| and ")) {
                    producer = producer.trim();
                    winnerProducers.computeIfAbsent(producer, k -> new ArrayList<>()).add(movie.getReleaseYear());
                }
            }
        }

        return winnerProducers;
    }

    private ProducerAwardIntervalsResponseDTO calculateAwardsInterval(Map<String, List<Long>> mapOfWinners) {
        List<ProducerAwardResponseDTO> intervals = new ArrayList<>();
        for (var entry : mapOfWinners.entrySet()) {
            List<Long> years = entry.getValue().stream().sorted().toList();
            String producer = entry.getKey();
            for (int i = 1; i < years.size(); i++) {
                intervals.add(ProducerAwardResponseDTO.builder()
                                .producers(producer)
                                .interval(years.get(i) - years.get(i - 1))
                                .previousWin(years.get(i - 1))
                                .followingWin(years.get(i))
                                .build()
                );
            }
        }

        if (intervals.isEmpty()){
            return ProducerAwardIntervalsResponseDTO.builder().build();
        }
        long min = intervals.stream().mapToLong(ProducerAwardResponseDTO::getInterval).min().orElse(0);
        long max = intervals.stream().mapToLong(ProducerAwardResponseDTO::getInterval).max().orElse(0);

        return ProducerAwardIntervalsResponseDTO.builder()
                .min(intervals.stream().filter(i -> i.getInterval() == min).toList())
                .max(intervals.stream().filter(i -> i.getInterval() == max).toList())
                .build();
    }
}
