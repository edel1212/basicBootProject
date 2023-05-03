package com.yoo.basicBoot.repository.movie.support;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchMovieRepository {
    Page<Object[]> getMovieList(String type, String searchText,Pageable pageable);
}
