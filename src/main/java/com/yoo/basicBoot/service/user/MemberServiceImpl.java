package com.yoo.basicBoot.service.user;

import com.yoo.basicBoot.dto.user.MemberAuthDTO;
import com.yoo.basicBoot.dto.user.MemberDTO;
import com.yoo.basicBoot.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public String registerMember(MemberDTO memberDTO) {
//        Member member = this.dtoToEntity(memberDTO);
 //       memberRepository.save(member);
        return null;
    }

    @Override
    public Long findMemberByEmail(String email) {
        return memberRepository.countByEmail(email);
    }

}
