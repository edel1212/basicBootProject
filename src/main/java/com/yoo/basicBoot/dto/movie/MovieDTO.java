package com.yoo.basicBoot.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieDTO {
    private Long mno              ;// 시퀀스
    private String popularity     ;// 인기점수
    private String originalTitle ;// 오리지널 타이틀
    private String overview       ;// 요약
    private String posterPath    ;// 포스터
    private String backdropPath  ;// 배경이미지
    private String title          ;// 제목
    private String releaseDate   ;// 개봉일
    private String id             ;// ID
    private String genre          ;// 장르변환값
    private String comment        ;// 코멘트

    //BaseEntity에 추가된 변수
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
