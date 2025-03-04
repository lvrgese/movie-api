package com.lvrgese.movie_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @NotBlank(message = "Title is required")
    @Column(nullable = false,length = 200)
    private String title;

    @NotBlank(message = "Director name is required")
    @Column(nullable = false)
    private String directorName;

    @NotBlank(message = "Studio name is required")
    @Column(nullable = false)
    private String studioName;

    @ElementCollection
    @CollectionTable(name="movie_cast")
    private Set<String> movieCast;

    @Positive(message = "Value must be greater than 0")
    private Integer releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "poster is required")
    private String poster;
}
