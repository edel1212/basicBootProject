package com.yoo.basicBoot.repository.movie.service;

import com.yoo.basicBoot.dto.movie.MoviePageRequestDTO;
import com.yoo.basicBoot.service.movie.MovieService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Log4j2
public class MovieServiceTests {

    @Autowired
    private MovieService movieService;
    @Test
    public void getMovieList(){

        MoviePageRequestDTO pageRequestDTO
                = MoviePageRequestDTO.builder().page(1).size(10).build();
        movieService.getMovieList(pageRequestDTO).getDtoList();
    }


}
