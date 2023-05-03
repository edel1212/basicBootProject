package com.yoo.basicBoot.service.user;

import com.yoo.basicBoot.dto.user.MemberAuthDTO;

public interface MemberService {
    String registerMember(MemberAuthDTO memberDTO);

}
