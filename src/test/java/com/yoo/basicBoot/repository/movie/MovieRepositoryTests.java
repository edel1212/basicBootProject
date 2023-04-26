package com.yoo.basicBoot.repository.movie;

import com.yoo.basicBoot.entity.movie.Movie;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void movieInsertTest(){

        Movie movie = Movie.builder()
                .backdropPath("test")
                .id(1L)
                .genre("테스트장르")
                .releaseDate("2022-02-23")
                .comment("테스트입니다.")
                .title("ㅋㅋ")
                .overview("임시")
                .build();
        movieRepository.save(movie);
        log.info("mno!!!:: {}", movie.getMno());

    }

}
