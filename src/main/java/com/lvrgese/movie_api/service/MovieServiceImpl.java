package com.lvrgese.movie_api.service;

import com.lvrgese.movie_api.dto.MovieDTO;
import com.lvrgese.movie_api.entity.Movie;
import com.lvrgese.movie_api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        Movie movie = Movie.builder()
                .title(movieDTO.getTitle())
                .directorName(movieDTO.getDirectorName())
                .studioName(movieDTO.getStudioName())
                .movieCast(movieDTO.getMovieCast())
                .releaseYear(movieDTO.getReleaseYear())
                .poster(movieDTO.getPoster())
                .build();
        var savedMovie = movieRepository.save(movie);

        String posterUrl= baseUrl+uploadedFIleName;
        return  MovieDTO.builder()
                .title(savedMovie.getTitle())
                .directorName(savedMovie.getDirectorName())
                .studioName(savedMovie.getStudioName())
                .movieCast(savedMovie.getMovieCast())
                .releaseYear(savedMovie.getReleaseYear())
                .poster(savedMovie.getPoster())
                .posterUrl(posterUrl)
                .build();

    }

    @Override
    public MovieDTO getMovie(Integer movieId) {
        return null;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        return List.of();
    }
}
