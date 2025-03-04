package com.lvrgese.movie_api.repository;

import com.lvrgese.movie_api.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Integer> {
}
