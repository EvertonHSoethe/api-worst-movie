package com.outsera.api_worst_movie.controller;

import com.outsera.api_worst_movie.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/internal")
public class InternalController {

    public final MovieService movieService;

    public InternalController(MovieService movieService){
        this.movieService = movieService;
    }

    @PostMapping(value = "/movies/upload-csv")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) throws IOException {
        return movieService.uploadCsv(file);
    }
}
