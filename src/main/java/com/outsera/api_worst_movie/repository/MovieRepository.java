package com.outsera.api_worst_movie.repository;

import com.outsera.api_worst_movie.dto.ProducerWinnerDTO;
import com.outsera.api_worst_movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {


    @Query("SELECT new com.outsera.api_worst_movie.dto.ProducerWinnerDTO(m.producers, m.releaseYear) " +
            "FROM Movie m WHERE m.winner = true ORDER BY m.releaseYear ASC")
    List<ProducerWinnerDTO> findWinnerProducersOrdered();
}
