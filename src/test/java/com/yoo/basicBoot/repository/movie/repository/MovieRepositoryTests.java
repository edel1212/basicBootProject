package com.yoo.basicBoot.repository.movie.repository;

import com.yoo.basicBoot.entity.movie.Movie;
import com.yoo.basicBoot.repository.movie.MovieRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void movieInsertTest(){

        IntStream.rangeClosed(1, 100).forEach(item ->{
            Movie movie = Movie.builder()
                    .backdropPath("/ixrYaoKtLO6HpciDbb4QGj3D3tT.jpg")
                    .genre("애니메이션,모험,액션,판타지")
                    .id(900667L)
                    .originalTitle("ONE PIECE FILM RED")
                    .overview("오직 목소리 하나로 전 세계를 사로잡은 디바 ‘우타’.  그녀가 모습을 드러내는 첫 라이브 콘서트가 음악의 섬 ‘엘레지아’에서 열리고  ‘루피’가 이끄는 밀짚모자 해적단과 함께 수많은 ‘우타’ 팬들로 공연장은 가득 찬다.  그리고 이 콘서트를 둘러싼 해적들과 해군들의 수상한 움직임이 시작되는데…  드디어 전세계가 애타게 기다리던 ‘우타’의 등장과 함께 노래가 울려 퍼지고,  그녀가 ‘샹크스의 딸’이라는 충격적인 사실이 드러난다.  ‘루피’, ‘우타’, ‘샹크스’, 세 사람의 과거가 그녀의 노랫소리와 함께 밝혀진다! 여기에서")
                    .popularity("101.703")
                    .posterPath("/pUEWvgIPGgx0Co9OQUwnKSfxYzQ.jpg")
                    .releaseDate("2022-02-23")
                    .title("원피스 필름 레드")
                    .comment("가나다라마바사"+ item)
                    .build();
            movieRepository.save(movie);
        });

    }

}
