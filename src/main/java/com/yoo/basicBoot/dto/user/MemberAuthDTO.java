package com.yoo.basicBoot.dto.user;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@Log4j2
public class MemberAuthDTO extends User {

    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    private String role;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public MemberAuthDTO(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean fromSocial) {
        super(username, password, authorities);
        // DataBase에 맞게 끔 생성자 메서드 생성 시 추가
        this.email = username;
        this.fromSocial = fromSocial;
        this.password = password;
    }
}
