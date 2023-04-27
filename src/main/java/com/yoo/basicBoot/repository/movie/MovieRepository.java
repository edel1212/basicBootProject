package com.yoo.basicBoot.repository.movie;

import com.yoo.basicBoot.entity.movie.Movie;
import com.yoo.basicBoot.repository.movie.dsl.SearchMovieRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> , SearchMovieRepository {
}
