package com.yoo.basicBoot.security.service;

import com.yoo.basicBoot.common.MemberState;
import com.yoo.basicBoot.security.dto.MemberAuthDTO;
import com.yoo.basicBoot.entity.user.Member;
import com.yoo.basicBoot.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("-------------------");
        log.info("UserDerailService username(Email)::: {} ", username);

        // 해당 로직은 기본 로그인 시 에 접근될 로직이므로 소셜은 무조건 false 이다.
        Member findMember = memberRepository.findByEmail(username, false);

        if(findMember == null ){
            throw new UsernameNotFoundException("userNotFound");
        }  else if(MemberState.E.toString().equals(findMember.getState())){
            throw new BadCredentialsException("uuidCheck");
        }  else if(!MemberState.P.toString().equals(findMember.getState())){
            throw new BadCredentialsException("pwCount");
        }

        MemberAuthDTO memberAuthDTO = new MemberAuthDTO(
                findMember.getEmail()
                , findMember.getPassword()
                , Arrays.asList(new SimpleGrantedAuthority("ROLE_"+findMember.getRole()))
                , findMember.isFromSocial()
        );
        // User를 상속 받은 객체레 전달이 가능 [다형성]
        return memberAuthDTO;
    }
}
