package com.lvrgese.movie_api.service;

import com.lvrgese.movie_api.dto.MovieDTO;
import com.lvrgese.movie_api.entity.Movie;
import com.lvrgese.movie_api.mappers.MovieMapper;
import com.lvrgese.movie_api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final FileService fileService;

    @Value("${poster.upload-dir}")
    private String uploadDir;

    @Value("${base.url.file}")
    private String baseUrl;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MovieDTO addMovie(MovieDTO movieDTO, MultipartFile file) throws IOException {

        String uploadedFIleName = fileService.uploadFile(uploadDir,file);
        movieDTO.setPoster(uploadedFIleName);

        Movie movie = MovieMapper.toMovie(movieDTO);
        var savedMovie = movieRepository.save(movie);

        String posterUrl= baseUrl+uploadedFIleName;
        MovieDTO responseDto = MovieMapper.toMovieDTO(savedMovie);
        responseDto.setPoster(posterUrl);
        return responseDto;
    }

    @Override
    public MovieDTO getMovie(Integer movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() ->
                new RuntimeException("Movie not found"));
        MovieDTO responseDto = MovieMapper.toMovieDTO(movie);
        responseDto.setPosterUrl(baseUrl+movie.getPoster());
        return responseDto;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDTO> responseList=new ArrayList<>();
        for(Movie m : movies){
            MovieDTO temp = MovieMapper.toMovieDTO(m);
            temp.setPosterUrl(baseUrl+m.getPoster());
            responseList.add(temp);
        }
        return  responseList;
    }
}
