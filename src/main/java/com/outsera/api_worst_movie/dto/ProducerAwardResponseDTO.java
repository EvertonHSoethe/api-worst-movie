package com.outsera.api_worst_movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProducerAwardResponseDTO {
    private String producer;
    private Long interval;
    private Long previousWin;
    private Long followingWin;
}
