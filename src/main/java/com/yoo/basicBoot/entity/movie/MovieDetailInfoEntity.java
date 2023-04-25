package com.yoo.basicBoot.entity.movie;

import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class MovieDetailInfoEntity {
    private String popularity     ;// 인기점수
    private String original_title ;// 오리지널 타이틀
    private String overview       ;// 요약
    private String poster_path    ;// 포스터
    private String backdrop_path  ;// 배경이미지
    private String title          ;// 제목
    private String release_date   ;// 개봉일
    private String id             ;// ID
    private String genre          ;// 장르변환값
    private String comment        ;// 코멘트
}
