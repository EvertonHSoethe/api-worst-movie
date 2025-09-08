package com.outsera.api_worst_movie.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "release_year")
    private Long releaseYear;
    private String title;
    private String studio;
    private String producer;
    private boolean worstMovieWinner;

//    public Movie(Long year, String title, String studio, String producer, boolean worstMovieWinner) {
//        this.releaseYear = year;
//        this.title = title;
//        this.studio = studio;
//        this.producer = producer;
//        this.worstMovieWinner = worstMovieWinner;
//    }
}
