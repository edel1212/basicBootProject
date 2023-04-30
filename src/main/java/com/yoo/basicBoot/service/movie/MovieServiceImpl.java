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

        // 정렬 조건 추가
        Sort sort = null;
        String sortType = moviePageRequestDTO.getSortType();
        if(sortType != null){
            String sortKey = "";
            sort = Sort.by(MovieServiceImpl.parseSortType(sortType)).descending()
                    .and(Sort.by("mno")).descending();
        } else{
            sort = Sort.by("mno").descending();
        }

        Pageable pageable = moviePageRequestDTO.getPageable(sort);

        // 조회 조건 추가
        Page<Object[]> result = movieRepository.getMovieList( moviePageRequestDTO.getType()
                                                            , moviePageRequestDTO.getSearchText()
                                                            , pageable );

        Function<Object[], MovieDTO> fn = arr -> this.entityToDto((Movie) arr[0], (Long) arr[1]);

        return new MoviePageResultDTO<>(result, fn);
    }

    @Override
    public MovieDTO getMovieDetail(Long mno) {
        return this.entityToDto(movieRepository.findById(mno).get());
    }


    /**
     * 받아온 정렬 조회 key를 컬럼으로 변환
     * - r  : 개봉일자
     * - m  : 수정일자
     * - p  : 인기점수
     * - rv : 리뷰 수
     * @param sortType
     * @return  String
     * */
    private static String parseSortType(String sortType){
        String result = "";
        switch (sortType) {
            case "r" :
                result = "releaseDate";
                break;
            case "m" :
                result = "modDate";
                break;
            case "p" :
                result = "popularity";
                break;
            case "rv" :
                result = "replyCnt";
                break;
        }
        return result;
    }
}
