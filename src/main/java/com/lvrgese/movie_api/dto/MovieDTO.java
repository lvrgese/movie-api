package com.lvrgese.movie_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDTO {
    private Integer movieId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Director name is required")
    private String directorName;

    @NotBlank(message = "Studio name is required")
    private String studioName;

    private Set<String> movieCast;

    private Integer releaseYear;

    @NotBlank(message = "poster is required")
    private String poster;

    @NotBlank(message = "poster is required")
    private String posterUrl;
}
