package com.yoo.basicBoot.security.dto;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

/**
 * <p>UserDetailsService 와 OAuthDetailsService를 위한 DTO</p>
 * <p> - 상속받는 User의 경우 UserDetailsService를 위해 사용된다.</p>
 * <p> - 구현하는 OAuth2User 경우 OAuth2DetailsService를 위해 사용된다.</p>
 * <p>
 *     <strong>
 *         각각의 유형마다 생성자 메서드가 다르나 OAuth에서 사용될 생성자메서드는 <br/>
 *         User에서 사용될 생성자를 호출하여 생성함
 *     </strong>
 * </p>
 * */
@Getter
@Setter
@Log4j2
public class MemberAuthDTO extends User implements OAuth2User {

    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    private String role;

    private LocalDateTime regDate;
    private LocalDateTime modDate;


    //////////////

    // OAuth2로그인 성공 시 모든 정보를 갖고있음
    private Map<String, Object> attrs;

    // UserDetailsService 사용
    public MemberAuthDTO(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean fromSocial) {
        super(username, password, authorities);
        // DataBase에 맞게 끔 생성자 메서드 생성 시 추가
        this.email = username;
        this.fromSocial = fromSocial;
        this.password = password;
    }

    // OAuth2DetailsServce에서 사용
    public MemberAuthDTO (String username, String password, Collection<? extends GrantedAuthority> authorities, boolean fromSocial, Map<String, Object> attrs) {
        this(username, password, authorities, fromSocial );
        this.attrs = attrs;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
}
