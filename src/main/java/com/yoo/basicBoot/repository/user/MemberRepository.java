package com.yoo.basicBoot.repository.user;

import com.yoo.basicBoot.entity.user.Member;
import com.yoo.basicBoot.repository.user.support.SearchMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String>, SearchMemberRepository {
    Long countByEmail(String email);
}
