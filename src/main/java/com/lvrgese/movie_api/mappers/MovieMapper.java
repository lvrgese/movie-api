package com.lvrgese.movie_api.mappers;

import com.lvrgese.movie_api.dto.MovieDto;
import com.lvrgese.movie_api.entity.Movie;

public class MovieMapper {

    public static MovieDto toMovieDto(Movie movie){
        if(movie == null)
            return null;
        return MovieDto.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .directorName(movie.getDirectorName())
                .studioName(movie.getStudioName())
                .releaseYear(movie.getReleaseYear())
                .movieCast(movie.getMovieCast())
                .poster(movie.getPoster())
                .build();
    }

    public static Movie toMovie(MovieDto movieDto){
        if(movieDto == null)
            return null;
        return Movie.builder()
                .movieId(movieDto.getMovieId())
                .title(movieDto.getTitle())
                .directorName(movieDto.getDirectorName())
                .studioName(movieDto.getStudioName())
                .releaseYear(movieDto.getReleaseYear())
                .movieCast(movieDto.getMovieCast())
                .poster(movieDto.getPoster())
                .build();
    }
}
