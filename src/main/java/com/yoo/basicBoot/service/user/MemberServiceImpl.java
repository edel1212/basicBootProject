package com.yoo.basicBoot.service.user;

import com.yoo.basicBoot.common.Roles;
import com.yoo.basicBoot.dto.user.MemberDTO;
import com.yoo.basicBoot.entity.user.Member;
import com.yoo.basicBoot.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public String registerMember(MemberDTO memberDTO) {
        // 소설 회원가입이 아님
        memberDTO.setFromSocial(false);
        // password Encode
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        // 권한 부여 Default USER
        memberDTO.setRole(Roles.USER.toString());
        Member member = this.dtoToEntity(memberDTO);
        memberRepository.save(member);
        return member.getEmail();
    }

    @Override
    public Long findMemberByEmail(String email) {
        return memberRepository.countByEmail(email);
    }

}
