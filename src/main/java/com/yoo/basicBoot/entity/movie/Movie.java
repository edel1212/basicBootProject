package com.yoo.basicBoot.entity.movie;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_movie")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
