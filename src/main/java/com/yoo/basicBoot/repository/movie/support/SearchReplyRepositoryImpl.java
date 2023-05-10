package com.yoo.basicBoot.repository.movie.support;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.yoo.basicBoot.entity.movie.QReply;
import com.yoo.basicBoot.entity.movie.Reply;
import com.yoo.basicBoot.entity.user.QMember;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class SearchReplyRepositoryImpl extends QuerydslRepositorySupport implements SearchReplyRepository  {
    public SearchReplyRepositoryImpl() {super(Reply.class);}

    @Override
    public List<Reply> getReplyList(Long mno) {

        QReply qReply = QReply.reply;
        QMember qMember = QMember.member;

        JPQLQuery<Reply> jpqlQuery = from(qReply);

        jpqlQuery.leftJoin(qReply.member, qMember).fetchJoin();

        jpqlQuery.select(qReply);

        jpqlQuery.where(qReply.movie.mno.eq(mno));

        // rno 기준 정렬
        PathBuilder<Reply> pathbuilder = new PathBuilder<Reply>(Reply.class,"reply");
        jpqlQuery.orderBy(new OrderSpecifier(Order.DESC,pathbuilder.get("rno")));

        return jpqlQuery.fetch();
    }
}
