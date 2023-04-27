package com.yoo.basicBoot.service.movie;

import com.yoo.basicBoot.dto.movie.MovieDTO;
import com.yoo.basicBoot.dto.movie.MoviePageRequestDTO;
import com.yoo.basicBoot.dto.movie.MoviePageResultDTO;
import com.yoo.basicBoot.entity.movie.Movie;

public interface MovieService {
    Long insertMovie(MovieDTO movieDTO);

    MoviePageResultDTO<MovieDTO, Object[]> getMovieList(MoviePageRequestDTO moviePageRequestDTO);

    default Movie dtoToEntity(MovieDTO movieDTO){
        return Movie.builder()
                .mno(movieDTO.getMno())
                .overview(movieDTO.getOverview())
                .title(movieDTO.getTitle())
                .genre(movieDTO.getGenre())
                .comment(movieDTO.getComment())
                .releaseDate(movieDTO.getReleaseDate())
                .backdropPath(movieDTO.getBackdropPath())
                .originalTitle(movieDTO.getOriginalTitle())
                .id(movieDTO.getId())
                .popularity(movieDTO.getPopularity())
                .posterPath(movieDTO.getPosterPath())
                .build();
    }

    default MovieDTO entityToDto(Movie movie){
        return MovieDTO.builder()
                .mno(movie.getMno())
                .overview(movie.getOverview())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .comment(movie.getComment())
                .releaseDate(movie.getReleaseDate())
                .backdropPath(movie.getBackdropPath())
                .originalTitle(movie.getOriginalTitle())
                .id(movie.getId())
                .popularity(movie.getPopularity())
                .posterPath(movie.getPosterPath())
                .modDate(movie.getModDate())
                .regDate(movie.getRegDate())
                .build();
    }

}
