package com.yoo.basicBoot.config;


import com.yoo.basicBoot.entity.user.Member;
import com.yoo.basicBoot.repository.user.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Log4j2
public class SecurityConfigTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Description("PasswordEncoder Bean 주입 확인")
    @Test
    public void passwordEncodeTest(){
        String pw = "1111";

        String encode = passwordEncoder.encode(pw);
        log.info("encode ::: {}", encode);
        log.info("match Result ::: {}" ,passwordEncoder.matches(pw, encode));

    }


    @Description("매칭확인")
    @Test
    public void passwordMatchTests(){
        String pw = "111";

        Member member = memberRepository.findByEmail("edel1@naver.com",false);
        String pwDB = member.getPassword();

        log.info("pwDB ::: {}", pwDB);

        log.info("match Result ::: {}" ,passwordEncoder.matches(pw, pwDB));

    }

}
