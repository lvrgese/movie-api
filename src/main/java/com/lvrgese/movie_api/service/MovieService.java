package com.lvrgese.movie_api.service;

import com.lvrgese.movie_api.dto.MovieDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MovieDTO addMovie(MovieDTO movieDTO, MultipartFile file) throws IOException;
    MovieDTO getMovie(Integer movieId);
    List<MovieDTO> getAllMovies();
}
