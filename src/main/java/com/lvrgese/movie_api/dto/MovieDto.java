package com.lvrgese.movie_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {
    private Integer movieId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Director name is required")
    private String directorName;

    @NotBlank(message = "Studio name is required")
    private String studioName;

    @NotNull
    private Set<String> movieCast;

    @Positive
    private Integer releaseYear;

    @NotBlank(message = "poster is required")
    private String poster;

    private String posterUrl;
}
