package com.outsera.api_worst_movie.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProducerAwardIntervalsResponseDTO {
    private List<ProducerAwardResponseDTO> min;
    private List<ProducerAwardResponseDTO> max;
}
