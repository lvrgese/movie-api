package com.lvrgese.movie_api.service;

import com.lvrgese.movie_api.dto.MovieDto;
import com.lvrgese.movie_api.dto.MoviePageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;
    MovieDto getMovie(Integer movieId);
    List<MovieDto> getAllMovies();
    MovieDto updateMovieById(Integer id, MovieDto movieDto,MultipartFile file) throws IOException;
    String deleteMovieById(Integer id) throws IOException;
    MoviePageResponse getAllMoviesWithPagination(Integer pageNumber,Integer pageSize);
    MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber,Integer pageSize,String sortBy, String sortDir);
}
