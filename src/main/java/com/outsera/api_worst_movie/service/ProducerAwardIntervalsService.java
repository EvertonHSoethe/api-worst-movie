package com.outsera.api_worst_movie.service;

import com.outsera.api_worst_movie.dto.ProducerAwardIntervalsResponseDTO;
import com.outsera.api_worst_movie.dto.ProducerAwardResponseDTO;
import com.outsera.api_worst_movie.dto.ProducerWinnerDTO;
import com.outsera.api_worst_movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProducerAwardIntervalsService {

    private final MovieRepository movieRepository;

    public ProducerAwardIntervalsResponseDTO getProducerAwardIntervals() {
        var winnersList = movieRepository.findWinnerProducersOrdered();
        var mapOfWinners = transformWinnersToMap(winnersList);
        return calculateAwardsInterval(mapOfWinners);
    }

    private Map<String, List<Long>> transformWinnersToMap(List<ProducerWinnerDTO> winnersList) {
        return winnersList.stream()
                .flatMap(winner -> Arrays.stream(winner.producers().split("\\s*(?:,|and)\\s*"))
                        .map(String::trim)
                        .map(producer -> Map.entry(producer, winner.releaseYear())))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
    }

    private ProducerAwardIntervalsResponseDTO calculateAwardsInterval(Map<String, List<Long>> mapOfWinners) {
        List<ProducerAwardResponseDTO> intervals = mapOfWinners.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .flatMap(entry -> {
                    List<Long> years = entry.getValue();
                    List<ProducerAwardResponseDTO> list = new ArrayList<>();
                    for (int i = 1; i < years.size(); i++) {
                        long previousWin = years.get(i - 1);
                        long followingWin = years.get(i);
                        list.add(ProducerAwardResponseDTO.builder()
                                .producer(entry.getKey())
                                .interval(years.get(i) - years.get(i - 1))
                                .previousWin(previousWin)
                                .followingWin(followingWin)
                                .build());
                    }
                    return list.stream();
                })
                .toList();

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
