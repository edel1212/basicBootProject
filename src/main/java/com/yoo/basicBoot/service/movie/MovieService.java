package com.yoo.basicBoot.service.movie;

import com.yoo.basicBoot.dto.movie.MovieDTO;
import com.yoo.basicBoot.dto.movie.MoviePageRequestDTO;
import com.yoo.basicBoot.dto.movie.MoviePageResultDTO;
import com.yoo.basicBoot.entity.movie.Movie;
import com.yoo.basicBoot.entity.movie.Reply;

public interface MovieService {
    //등록
    Long insertMovie(MovieDTO movieDTO);

    //목록
    MoviePageResultDTO<MovieDTO, Object[]> getMovieList(MoviePageRequestDTO moviePageRequestDTO);

    //단건
    MovieDTO getMovieDetail(Long mno);

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

    default MovieDTO entityToDto(Movie movie, Long reply){
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
                .replyCnt(reply.intValue())
                .modDate(movie.getModDate())
                .regDate(movie.getRegDate())
                .build();
    }



}
