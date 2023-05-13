package com.yoo.basicBoot.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    private String role;

    private String state;

    //BaseEntity에 추가된 변수
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
