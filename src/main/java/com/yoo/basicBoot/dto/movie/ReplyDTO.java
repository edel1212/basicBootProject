package com.yoo.basicBoot.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDTO {

    private Long rno           ;// 시퀀스
    private Long mno           ;// 영화번호
    private String text        ;// 댓글
    private String replier     ;// 작성자

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
