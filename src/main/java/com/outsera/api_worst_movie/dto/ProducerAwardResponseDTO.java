package com.outsera.api_worst_movie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProducerAwardResponseDTO {
    private String producers;
    private Long interval;
    private Long previousWin;
    private Long followingWin;
}
