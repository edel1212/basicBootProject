package com.yoo.basicBoot.service.movie;

import com.yoo.basicBoot.dto.movie.MovieDTO;
import com.yoo.basicBoot.entity.movie.Movie;
import com.yoo.basicBoot.repository.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    @Override
    public Long insertMovie(MovieDTO movieDTO) {
        Movie movie = this.dtoToEntity(movieDTO);
        movieRepository.save(movie);
        return movie.getMno();
    }
}
