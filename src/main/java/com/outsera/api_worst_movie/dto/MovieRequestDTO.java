package com.outsera.api_worst_movie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieRequestDTO {
    private Long releaseYear;
    private String title;
    private String studios;
    private String producers;
    private boolean winner;
}
