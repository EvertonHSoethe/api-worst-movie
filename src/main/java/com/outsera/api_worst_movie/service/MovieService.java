package com.outsera.api_worst_movie.service;

import com.outsera.api_worst_movie.dto.MovieRequestDTO;
import com.outsera.api_worst_movie.mapper.MovieMapper;
import com.outsera.api_worst_movie.model.Movie;
import com.outsera.api_worst_movie.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final JdbcTemplate jdbcTemplate;

    public Movie save(MovieRequestDTO movieRequestDTO) {
        return movieRepository.save(MovieMapper.toEntity(movieRequestDTO));
    }

    public ResponseEntity<String> uploadCsv(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty file");
        }

        if (!isCsvHeaderValid(file)) {
            return ResponseEntity.badRequest().body("CSV header is invalid. Expected: YEAR,TITLE,STUDIOS,PRODUCERS,WINNER");
        }

        File tempFile = File.createTempFile("movies-", ".csv");
        file.transferTo(tempFile);

        String sql = "INSERT INTO MOVIE (RELEASE_YEAR, TITLE, STUDIOS, PRODUCERS, WINNER) " +
                "SELECT YEAR, TITLE, STUDIOS, PRODUCERS, WINNER FROM CSVREAD('" +
                tempFile.getAbsolutePath().replace("\\", "/") + "')";

        jdbcTemplate.execute(sql);

        tempFile.delete();

        return ResponseEntity.ok("CSV imported successfully!");
    }

    private boolean isCsvHeaderValid(MultipartFile file) {
        try (Scanner scanner = new Scanner(file.getInputStream())){
            if (!scanner.hasNextLine()) {
                return false;
            }
            String header = scanner.nextLine().trim();
            return header.equalsIgnoreCase("year;title;studios;producers;winner");
        } catch (IOException e) {
            return false;
        }
    }
}
