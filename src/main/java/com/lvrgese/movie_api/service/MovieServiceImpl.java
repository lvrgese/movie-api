package com.lvrgese.movie_api.service;

import com.lvrgese.movie_api.dto.MovieDto;
import com.lvrgese.movie_api.entity.Movie;
import com.lvrgese.movie_api.mappers.MovieMapper;
import com.lvrgese.movie_api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {

        if(Files.exists(Paths.get(uploadDir+ File.separator+file.getOriginalFilename()))){
            throw new RuntimeException("Filename already exists. Try another name");
        }
        String uploadedFIleName = fileService.uploadFile(uploadDir,file);
        movieDto.setPoster(uploadedFIleName);

        Movie movie = MovieMapper.toMovie(movieDto);
        movie.setMovieId(null);
        var savedMovie = movieRepository.save(movie);

        String posterUrl= baseUrl+uploadedFIleName;
        MovieDto responseDto = MovieMapper.toMovieDto(savedMovie);
        responseDto.setPoster(posterUrl);
        return responseDto;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() ->
                new RuntimeException("Movie not found"));
        MovieDto responseDto = MovieMapper.toMovieDto(movie);
        responseDto.setPosterUrl(baseUrl+movie.getPoster());
        return responseDto;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> responseList=new ArrayList<>();
        for(Movie m : movies){
            MovieDto temp = MovieMapper.toMovieDto(m);
            temp.setPosterUrl(baseUrl+m.getPoster());
            responseList.add(temp);
        }
        return  responseList;
    }

    @Override
    public MovieDto updateMovieById(Integer id, MovieDto movieDto, MultipartFile file) throws IOException {
        Movie movie = movieRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Movie not found"));
        if(file != null){
            Files.deleteIfExists(Paths.get(uploadDir+File.separator+movie.getPoster()));
        }
        String updatedFileName = fileService.uploadFile(uploadDir,file);

        movie.setTitle(movieDto.getTitle());
        movie.setDirectorName(movieDto.getDirectorName());
        movie.setReleaseYear(movieDto.getReleaseYear());
        movie.setStudioName(movieDto.getStudioName());
        movie.setMovieCast(movieDto.getMovieCast());
        movie.setPoster(updatedFileName);

        Movie savedMovie = movieRepository.save(movie);

        MovieDto responseDto = MovieMapper.toMovieDto(savedMovie);
        responseDto.setPosterUrl(baseUrl+updatedFileName);
        return responseDto;
    }

    @Override
    public String deleteMovieById(Integer id) throws IOException {
        Movie movie = movieRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Movie not found"));
        Files.deleteIfExists(Paths.get(uploadDir+File.separator+movie.getPoster()));
        movieRepository.deleteById(id);
        return "Successfully deleted item with id : "+id;
    }
}
