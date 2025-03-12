package com.lvrgese.movie_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvrgese.movie_api.dto.MovieDto;
import com.lvrgese.movie_api.dto.MoviePageResponse;
import com.lvrgese.movie_api.service.MovieService;
import com.lvrgese.movie_api.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Movies",description = "Operations related to movies")
public class MovieController {

    private final MovieService movieService;
    private final Validator validator;

    public MovieController(MovieService movieService, Validator validator) {
        this.movieService = movieService;
        this.validator = validator;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/movie")
    @Operation(summary = "Add Movie", description = "This endpoint is only available for admin")
    @ApiResponse(responseCode ="201", description = "Successfully created a new movie")
    public ResponseEntity<?> addMovie(@RequestPart MultipartFile file,@RequestPart String movieDto) throws IOException {

        MovieDto dto = new ObjectMapper().readValue(movieDto,MovieDto.class);
        Set<ConstraintViolation<MovieDto>> violations = validator.validate(dto);
        if(!violations.isEmpty())
            return ResponseEntity.badRequest().body("Some fields in the DTO is either missing or invalid");
        if(file.isEmpty())
            return ResponseEntity.badRequest().body("File is required");
        return new ResponseEntity<> (movieService.addMovie(dto,file), HttpStatus.CREATED);
    }

    @Operation(summary = "Get movie by Id", description = "Retrieve a movie by its Id")
    @ApiResponse(responseCode ="200", description = "Successfully retrieved the movie")
    @GetMapping("/movie/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Integer id){
        return ResponseEntity.ok(movieService.getMovie(id));
    }

    @GetMapping("/movies")
    @Operation(summary = "Get all movies", description = "Get all movies in the database as a List")
    @ApiResponse(responseCode ="200")
    public ResponseEntity<List<MovieDto>> getAllMovies(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/movie/{id}")
    @Operation(summary = "Update a movie", description = "Update the movie poster and/or info about the movie")
    @ApiResponse(responseCode ="200",description = "Successfully updated the movie")
    public ResponseEntity<?> updateMovie(@PathVariable Integer id, @RequestPart MultipartFile file, @RequestPart String movieDto) throws IOException {
        MovieDto dto = new ObjectMapper().readValue(movieDto,MovieDto.class);
        Set<ConstraintViolation<MovieDto>> violations = validator.validate(dto);
        if(!violations.isEmpty())
            return ResponseEntity.badRequest().body("Some fields in the DTO is either missing or invalid");
        if(file.isEmpty())
            file=null;
        return ResponseEntity.ok(movieService.updateMovieById(id, dto, file));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/movie/{id}")
    @Operation(summary = "Delete a movie", description = "Delete a movie by its Id")
    @ApiResponse(responseCode ="200",description = "Successfully deleted the movie")
    public ResponseEntity<String> deleteMovie(@PathVariable Integer id) throws IOException {
        return  ResponseEntity.ok(movieService.deleteMovieById(id));
    }

    @GetMapping("movies/paged")
    @Operation(summary = "Get movies using pagination", description = "Get movies by page and size (Default is page:0 and size:5)")
    @ApiResponse(responseCode ="200")
    public ResponseEntity<MoviePageResponse> getAllMoviesWithPagination(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) Integer page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) Integer size
    ){
        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(page,size));
    }

    @Operation(summary = "Get movies using pagination and sorting", description = "Get movies by page and size as sorted list" +
            " (Default is page:0 and size:5 sortBy:title dir:asc)")
    @ApiResponse(responseCode ="200")
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
