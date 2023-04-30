package com.yoo.basicBoot.repository.movie.dsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.yoo.basicBoot.entity.movie.Movie;
import com.yoo.basicBoot.entity.movie.QMovie;
import com.yoo.basicBoot.entity.movie.QReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class SearchMovieRepositoryImpl extends QuerydslRepositorySupport implements SearchMovieRepository {

    public SearchMovieRepositoryImpl() {
        super(Movie.class);
    }

    @Override
    public Page<Object[]> getMovieList(String type,String searchText, Pageable pageable) {

        // Q도메인 생성
        QMovie qMovie = QMovie.movie;
        QReply qReply = QReply.reply;

        // from 대상 지정
        JPQLQuery<Movie> jpqlQuery = from(qMovie);

        // Join
        jpqlQuery.leftJoin(qReply).on(qReply.movie.eq(qMovie));

        // Tuple 생성 - Service에서 파싱 해줄것임
        JPQLQuery<Tuple> tuple =  jpqlQuery.select(qMovie,qReply.count());

        // 정렬
        Sort sort = pageable.getSort();

        sort.stream().distinct().forEach(order->{
            // 정렬 대상 Key
            String prop = order.getProperty();
            if("replyCnt".equals(prop)){
                tuple.orderBy(new OrderSpecifier<>(Order.DESC, qReply.count()));
            } else {
                // 정렬 방법
                Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                // tuple 정렬시 필요한 PathBuilder 생성
                PathBuilder<Movie> pathbuilder = new PathBuilder<Movie>(Movie.class,"movie");
                tuple.orderBy(new OrderSpecifier(direction, pathbuilder.get(prop)) );
            }
        });
        tuple.orderBy(new OrderSpecifier<>(Order.DESC, qMovie.mno));

        // 조회 조건 추가
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(type != null ){
            switch (type){
                case "g" :
                    booleanBuilder.or(qMovie.genre.contains(searchText));
                    break;
                case "w" : //TODO : 로그인 기능 구현 후 처리 필요
                    booleanBuilder.or(qMovie.backdropPath.contains(searchText));
                    break;
                case "t" :
                    booleanBuilder
                            .or(qMovie.title.contains(searchText))
                            .or(qMovie.originalTitle.contains(searchText));
                    break;
            }// switch
        }// if
        tuple.where(booleanBuilder);

        tuple.groupBy(qMovie);

        // 페이징
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());
        long count = tuple.fetchCount();

        // 리스트로 변환
        List<Tuple> result = tuple.fetch();

        // Tuple객체는 toArray로 변환해 줘야 사용하기 편함
        return new PageImpl( result.stream().map(Tuple::toArray).collect(Collectors.toList())
                            , pageable
                            , count );
    }
}
