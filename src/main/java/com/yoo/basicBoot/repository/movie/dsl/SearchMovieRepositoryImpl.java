package com.yoo.basicBoot.repository.movie.dsl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import com.yoo.basicBoot.entity.movie.Movie;
import com.yoo.basicBoot.entity.movie.QMovie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class SearchMovieRepositoryImpl extends QuerydslRepositorySupport implements SearchMovieRepository {

    public SearchMovieRepositoryImpl() {
        super(Movie.class);
    }

    @Override
    public Page<Object[]> getMovieList(Pageable pageable) {

        QMovie qMovie = QMovie.movie;

        JPQLQuery<Movie> jpqlQuery = from(qMovie);

//        JPQLQuery<Tuple> tuple =  jpqlQuery.select(qMovie.id,null);
//
//        List<Tuple> result = tuple.fetch();

//        long count = tuple.fetchCount();

        jpqlQuery.offset(pageable.getOffset());
        jpqlQuery.limit(pageable.getPageSize());

        return new PageImpl( jpqlQuery.fetch()
                            , pageable
                            , jpqlQuery.fetchCount() );
    }
}
