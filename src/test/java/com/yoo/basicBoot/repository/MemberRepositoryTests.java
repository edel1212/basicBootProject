package com.yoo.basicBoot.repository;

import com.yoo.basicBoot.repository.user.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;


    @Test
    public void findMemberByEmail(){
        log.info(memberRepository.findByEmail("edel1@naver.com",true));
    }

}
