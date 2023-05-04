package com.yoo.basicBoot.service.user;

import com.yoo.basicBoot.dto.user.MemberDTO;

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

}
