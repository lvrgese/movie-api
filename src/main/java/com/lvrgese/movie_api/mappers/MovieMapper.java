package com.lvrgese.movie_api.mappers;

import com.lvrgese.movie_api.dto.MovieDTO;
import com.lvrgese.movie_api.entity.Movie;

public class MovieMapper {

    public static MovieDTO toMovieDTO(Movie movie){
        if(movie == null)
            return null;
        return MovieDTO.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .directorName(movie.getDirectorName())
                .studioName(movie.getStudioName())
                .releaseYear(movie.getReleaseYear())
                .movieCast(movie.getMovieCast())
                .poster(movie.getPoster())
                .build();
    }

    public static Movie toMovie(MovieDTO movieDTO){
        if(movieDTO == null)
            return null;
        return Movie.builder()
                .movieId(movieDTO.getMovieId())
                .title(movieDTO.getTitle())
                .directorName(movieDTO.getDirectorName())
                .studioName(movieDTO.getStudioName())
                .releaseYear(movieDTO.getReleaseYear())
                .movieCast(movieDTO.getMovieCast())
                .poster(movieDTO.getPoster())
                .build();
    }
}
