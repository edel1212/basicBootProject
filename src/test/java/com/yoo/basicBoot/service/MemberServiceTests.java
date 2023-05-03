package com.yoo.basicBoot.service;

import com.yoo.basicBoot.common.Roles;
import com.yoo.basicBoot.dto.user.MemberAuthDTO;
import com.yoo.basicBoot.entity.user.Member;
import com.yoo.basicBoot.service.user.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void memberInsertTest(){
        IntStream.rangeClosed(1, 50).forEach(idx ->{
            String role = "";
            if(idx > 40){
                role = String.valueOf(Roles.ADMIN);
            } else if (idx > 20){
                role = String.valueOf(Roles.MANAGER);
            } else {
                role = String.valueOf(Roles.USER);
            }

            Member member = Member.builder()
                    .email("edel"+idx+"@naver.com")
                    .password(passwordEncoder.encode("111"))
                    .name("테스터"+1)
                    .role(role)
                    .fromSocial(false)
                    .build();
          //memberService.registerMember(member);
        });
    }

    @Test
    public void ttt9(){
        log.info(":::: {}",String.valueOf(Roles.ADMIN));
    }

}
