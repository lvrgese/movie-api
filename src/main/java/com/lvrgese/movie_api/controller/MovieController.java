package com.lvrgese.movie_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvrgese.movie_api.dto.MovieDTO;
import com.lvrgese.movie_api.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/movie")
    public ResponseEntity<MovieDTO> addMovie(@RequestPart MultipartFile file, @RequestPart String movieDTO) throws IOException {

        MovieDTO dto = new ObjectMapper().readValue(movieDTO,MovieDTO.class);
        return new ResponseEntity<> (movieService.addMovie(dto,file), HttpStatus.CREATED);
    }

}
