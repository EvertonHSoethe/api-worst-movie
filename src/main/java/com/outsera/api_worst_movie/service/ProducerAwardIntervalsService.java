package com.outsera.api_worst_movie.service;

import com.outsera.api_worst_movie.dto.ProducerAwardIntervalsResponseDTO;
import com.outsera.api_worst_movie.dto.ProducerAwardResponseDTO;
import com.outsera.api_worst_movie.dto.ProducerWinnerDTO;
import com.outsera.api_worst_movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProducerAwardIntervalsService {

    private final MovieRepository movieRepository;
    private static final Pattern PRODUCER_SPLIT_PATTERN = Pattern.compile("\\s*(?:,|and)\\s*");

    public ProducerAwardIntervalsResponseDTO getProducerAwardIntervals() {
        var winnersList = movieRepository.findWinnerProducersOrdered();
        var mapOfWinners = transformWinnersToMap(winnersList);
        return calculateAwardsInterval(mapOfWinners);
    }

    private Map<String, List<Long>> transformWinnersToMap(List<ProducerWinnerDTO> winnersList) {
        return winnersList.stream()
                .flatMap(winner -> PRODUCER_SPLIT_PATTERN.splitAsStream(winner.producers())
                        .map(String::trim)
                        .map(producer -> Map.entry(producer, winner.releaseYear())))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
    }

    private ProducerAwardIntervalsResponseDTO calculateAwardsInterval(Map<String, List<Long>> mapOfWinners) {

        List<ProducerAwardResponseDTO> minList = new ArrayList<>();
        List<ProducerAwardResponseDTO> maxList = new ArrayList<>();
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;

        for (Map.Entry<String, List<Long>> entry : mapOfWinners.entrySet()) {
            String producer = entry.getKey();
            List<Long> years = entry.getValue();

            if (years.size() < 2) continue;

            Collections.sort(years);
            long prev = years.get(0);

            for (int i = 1; i < years.size(); i++) {
                long next = years.get(i);
                long interval = next - prev;
                ProducerAwardResponseDTO dto = new ProducerAwardResponseDTO(producer, interval, prev, next);

                if (interval < min) {
                    min = interval;
                    minList.clear();
                    minList.add(dto);
                } else if (interval == min) {
                    minList.add(dto);
                }

                if (interval > max) {
                    max = interval;
                    maxList.clear();
                    maxList.add(dto);
                } else if (interval == max) {
                    maxList.add(dto);
                }

                prev = next;
            }
        }

        if (minList.isEmpty() && maxList.isEmpty()) {
            return ProducerAwardIntervalsResponseDTO.builder().build();
        }

        return ProducerAwardIntervalsResponseDTO.builder()
                .min(minList)
                .max(maxList)
                .build();
    }
}
