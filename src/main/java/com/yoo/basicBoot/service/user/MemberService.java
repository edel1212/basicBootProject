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
     * @return boolean
     * */
    boolean sendVerificationMail(MemberDTO memberDTO);

    /**
     * 인증 링크 이메일 발송
     * @param email, uuid
     * @return boolean
     * */
    boolean checkVerification(String email, String uuid);

    default MemberDTO entityToDTO(Member member){
        return MemberDTO.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .fromSocial(member.isFromSocial())
                .name(member.getName())
                .role(member.getRole())
                .state(member.getState())
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
                .state(memberDTO.getState())
                .role(memberDTO.getRole())
                .build();
    }
}
