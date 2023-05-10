package com.yoo.basicBoot.service.movie;

import com.yoo.basicBoot.dto.movie.MovieDTO;
import com.yoo.basicBoot.dto.movie.MoviePageRequestDTO;
import com.yoo.basicBoot.dto.movie.MoviePageResultDTO;
import com.yoo.basicBoot.entity.movie.Movie;
import com.yoo.basicBoot.entity.user.Member;
import com.yoo.basicBoot.security.dto.MemberAuthDTO;

import java.util.List;

public interface MovieService {
    //등록
    Long insertMovie(MovieDTO movieDTO, MemberAuthDTO memberAuthDTO);

    //목록
    MoviePageResultDTO<MovieDTO, Object[]> getMovieList(MoviePageRequestDTO moviePageRequestDTO);

    //단건
    MovieDTO getMovieDetail(Long mno);

    /**
     * <p>
     *     DTO를 Entity로 변환
     * </p>
     * @param movieDTO
     * @return Movie
     * */
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
                .member(Member.builder().email(movieDTO.getEmail()).build())
                .build();
    }

    /**
     * <p>
     *     Entity를 DTO로 변환
     * </p>
     * @param movie
     * @return MovieDTO
     * */
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
                .email(movie.getMember().getEmail())
                .modDate(movie.getModDate())
                .regDate(movie.getRegDate())
                .build();
    }

    /**
     * <p>
     *     Entity를 DTO로 변환 - 목록 가져올 경우 리뷰개수가 추가
     * </p>
     * @param movie, reply
     * @return MovieDTO
     * */
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
