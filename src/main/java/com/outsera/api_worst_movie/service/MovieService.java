package com.outsera.api_worst_movie.service;

import com.outsera.api_worst_movie.dto.MovieRequestDTO;
import com.outsera.api_worst_movie.mapper.MovieMapper;
import com.outsera.api_worst_movie.model.Movie;
import com.outsera.api_worst_movie.repository.MovieRepository;
import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void loadCsvOnStart() throws Exception {
        if (movieRepository.count() == 0) {
            try (var inputStream = getClass().getResourceAsStream("/static/Movielist.csv")) {
                if (inputStream != null) {
                    File tempFile = File.createTempFile("movies-init-", ".csv");
                    try {
                        java.nio.file.Files.copy(inputStream, tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        importCsv(tempFile);
                    } finally {
                        tempFile.delete();
                    }
                }
            }
        }
    }

    public ResponseEntity<String> uploadCsv(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty file");
        }

        jdbcTemplate.execute("DELETE FROM MOVIE");

        if (!isCsvHeaderValid(file)) {
            return ResponseEntity.badRequest().body("CSV header is invalid. Expected: year;title;studios;producers;winner");
        }

        File tempFile = File.createTempFile("movies-", ".csv");
        try {
            file.transferTo(tempFile);
            importCsv(tempFile);
        } finally {
            tempFile.delete();
        }

        return ResponseEntity.ok("CSV imported successfully!");
    }

    private void importCsv(File tempFile) {
        String sql = "INSERT INTO MOVIE (RELEASE_YEAR, TITLE, STUDIOS, PRODUCERS, WINNER) " +
                "SELECT \"YEAR\", TITLE, STUDIOS, PRODUCERS, COALESCE(WINNER, 'no') FROM CSVREAD('" +
                tempFile.getAbsolutePath().replace("\\", "/") +
                "', null, 'charset=UTF-8 fieldSeparator=;')";

        jdbcTemplate.execute(sql);
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
