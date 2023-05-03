package com.yoo.basicBoot.repository.user.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.yoo.basicBoot.entity.user.Member;
import com.yoo.basicBoot.entity.user.QMember;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class SearchMemberRepositoryImpl extends QuerydslRepositorySupport implements SearchMemberRepository{

    public SearchMemberRepositoryImpl() {super(Member.class);}

    @Override
    public Member findByEmail(String email, boolean fromSocial) {

        QMember qMember = QMember.member;

        JPQLQuery<Member> jpqlQuery = from(qMember);

        jpqlQuery.select(qMember);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qMember.email.eq(email).and(qMember.fromSocial.eq(fromSocial)));
        jpqlQuery.where(booleanBuilder);

        return jpqlQuery.fetchOne();
    }
}
