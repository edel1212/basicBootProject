package com.yoo.basicBoot.service.user;

import com.yoo.basicBoot.dto.user.MemberDTO;
import com.yoo.basicBoot.entity.user.Member;

public interface MemberService {

    /**
     * 회원가입
     * @param memberDTO
     * @return String
     * */
    String registerMember(MemberDTO memberDTO);

    /**
     * 회원 중복 조회
     * @param email
     * @return Long
     * */
    Long findMemberByEmail(String email);

    /**
     * 인증 링크 이메일 발송
     * @param memberDTO
     * */
    boolean sendVerificationMail(MemberDTO memberDTO);

    default MemberDTO entityToDTO(Member member){
        return MemberDTO.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .fromSocial(member.isFromSocial())
                .name(member.getName())
                .role(member.getRole())
                .modDate(member.getModDate())
                .regDate(member.getRegDate())
                .build();
    }

    default Member dtoToEntity(MemberDTO memberDTO){
        return Member.builder()
                .email(memberDTO.getEmail())
                .password(memberDTO.getPassword())
                .fromSocial(memberDTO.isFromSocial())
                .name(memberDTO.getName())
                .role(memberDTO.getRole())
                .build();
    }
}
