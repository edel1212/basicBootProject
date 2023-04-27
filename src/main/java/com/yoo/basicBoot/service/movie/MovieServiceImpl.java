package com.yoo.basicBoot.service.movie;

import com.yoo.basicBoot.dto.movie.MovieDTO;
import com.yoo.basicBoot.dto.movie.MoviePageRequestDTO;
import com.yoo.basicBoot.dto.movie.MoviePageResultDTO;
import com.yoo.basicBoot.entity.movie.Movie;
import com.yoo.basicBoot.repository.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

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

    @Override
    public MoviePageResultDTO<MovieDTO, Object[]> getMovieList(MoviePageRequestDTO moviePageRequestDTO) {

        Pageable pageable = moviePageRequestDTO.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getMovieList(pageable);

        Function<Object[], MovieDTO> fn = arr -> this.entityToDto((Movie) arr[0]);

        return new MoviePageResultDTO<>(result, fn);
    }
}
