package com.lvrgese.movie_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvrgese.movie_api.dto.MovieDto;
import com.lvrgese.movie_api.dto.MoviePageResponse;
import com.lvrgese.movie_api.service.MovieService;
import com.lvrgese.movie_api.utils.AppConstants;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class MovieController {

    private final MovieService movieService;
    private final Validator validator;

    public MovieController(MovieService movieService, Validator validator) {
        this.movieService = movieService;
        this.validator = validator;
    }

    @PostMapping("/movie")
    public ResponseEntity<?> addMovie(@RequestPart MultipartFile file,@RequestPart String movieDto) throws IOException {

        MovieDto dto = new ObjectMapper().readValue(movieDto,MovieDto.class);
        Set<ConstraintViolation<MovieDto>> violations = validator.validate(dto);
        if(!violations.isEmpty())
            return ResponseEntity.badRequest().body("Some fields in the DTO is either missing or invalid");
        if(file.isEmpty())
            return ResponseEntity.badRequest().body("File is required");
        return new ResponseEntity<> (movieService.addMovie(dto,file), HttpStatus.CREATED);
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Integer id){
        return ResponseEntity.ok(movieService.getMovie(id));
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieDto>> getAllMovies(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PutMapping("/movie/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Integer id, @RequestPart MultipartFile file, @RequestPart String movieDto) throws IOException {
        MovieDto dto = new ObjectMapper().readValue(movieDto,MovieDto.class);
        Set<ConstraintViolation<MovieDto>> violations = validator.validate(dto);
        if(!violations.isEmpty())
            return ResponseEntity.badRequest().body("Some fields in the DTO is either missing or invalid");
        if(file.isEmpty())
            file=null;
        return ResponseEntity.ok(movieService.updateMovieById(id, dto, file));
    }

    @DeleteMapping("/movie/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Integer id) throws IOException {
        return  ResponseEntity.ok(movieService.deleteMovieById(id));
    }

    @GetMapping("movies/paged")
    public ResponseEntity<MoviePageResponse> getAllMoviesWithPagination(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) Integer page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) Integer size
    ){
        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(page,size));
    }

    @GetMapping("movies/paged-sorted")
    public ResponseEntity<MoviePageResponse> getAllMoviesWithPaginationAndSorting(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) Integer page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) Integer size,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_COLUMN,required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String dir
    ){
        return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(page,size,sortBy,dir));
    }

}
