package com.outsera.api_worst_movie.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "movie",
        indexes = {
                @Index(name = "idx_movie_producer_winner_year", columnList = "winner, releaseYear")
        }
)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "release_year")
    private Long releaseYear;
    private String title;
    private String studios;
    private String producers;
    private boolean winner;
}
