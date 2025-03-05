package com.lvrgese.movie_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoviePageResponse {
    List<MovieDto> movieDtoList;
    Integer currentPage;
    Integer pageSize;
    Integer totalNumberOfPages;
    Integer totalNumberOfRecords;
    Boolean isLastPage;
}
