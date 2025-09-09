package com.outsera.api_worst_movie.controller;

import com.outsera.api_worst_movie.dto.ProducerAwardIntervalsResponseDTO;
import com.outsera.api_worst_movie.service.ProducerAwardIntervalsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/producers/award-intervals")
@RequiredArgsConstructor
public class ProducerAwardIntervalsController {

    public final ProducerAwardIntervalsService producerAwardIntervalsService;

    @GetMapping
    public ProducerAwardIntervalsResponseDTO getAwardIntervals() {
        return producerAwardIntervalsService.getProducerAwardIntervals();
    }
}
