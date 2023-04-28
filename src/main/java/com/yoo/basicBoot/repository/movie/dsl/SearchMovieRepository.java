package com.yoo.basicBoot.repository.movie.dsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchMovieRepository {
    Page<Object[]> getMovieList(Pageable pageable);
}
