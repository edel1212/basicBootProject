package com.yoo.basicBoot.security.service;

import com.yoo.basicBoot.dto.user.MemberDTO;
import com.yoo.basicBoot.entity.user.Member;
import com.yoo.basicBoot.repository.user.MemberRepository;
import com.yoo.basicBoot.security.dto.MemberAuthDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>OAuth 로그인 시 사용 되는 Service</p>
 * <p>Service지만 Interface가 아니며 DefaultOAuth2UserService를 상속받아</p>
 * <p>OAuth2User를 @Override 하여 구현중</p>
 * */
@Service
@Log4j2
@RequiredArgsConstructor
public class OAuth2DetailService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // OAuth2 사용된 API
        String clientName =  userRequest.getClientRegistration().getClientName();

        // 사용자 정보
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = "";
        if("Google".equals(clientName)){
            email = oAuth2User.getAttribute("email");
        }//if

        log.info("email ::: {}", email);

        // 정보 확인 후 저장
        Member socialMember = this.saveSocialMember(email);

        MemberAuthDTO memberAuthDTO = new MemberAuthDTO(
                socialMember.getEmail(),            // ID
                socialMember.getPassword(),         // PW
                Arrays.asList(new SimpleGrantedAuthority("ROLE_"+socialMember.getRole())), // ROLE
                true,                               // Social
                oAuth2User.getAttributes()          // allInfo
        );

        return memberAuthDTO;
    }

    /**
     * 소셜 로그인시 회원가입 유무를 파악
     * 없다면 회원가입, 존재한다면 반환
     * */
    private Member saveSocialMember(String email){
        Member result = memberRepository.findByEmail("email",true);

        if(result != null) return result;

        Member member = Member.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("111"))
                .fromSocial(true)
                .role("USER")
                .build();
        memberRepository.save(member);
        return member;
    }

}
