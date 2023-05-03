package com.yoo.basicBoot.repository.user.support;


import com.yoo.basicBoot.entity.user.Member;

public interface SearchMemberRepository {
    Member findByEmail(String email, boolean fromSocial);
}
