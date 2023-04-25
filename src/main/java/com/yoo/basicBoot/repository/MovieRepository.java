package com.yoo.basicBoot.repository;

import com.yoo.basicBoot.entity.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
