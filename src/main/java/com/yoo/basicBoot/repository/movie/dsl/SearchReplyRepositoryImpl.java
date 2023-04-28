package com.yoo.basicBoot.repository.movie.dsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.yoo.basicBoot.entity.movie.Movie;
import com.yoo.basicBoot.entity.movie.QReply;
import com.yoo.basicBoot.entity.movie.Reply;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class SearchReplyRepositoryImpl extends QuerydslRepositorySupport implements SearchReplyRepository  {
    public SearchReplyRepositoryImpl() {super(Reply.class);}

    @Override
    public List<Reply> getReplyList(Long mno) {

        QReply qReply = QReply.reply;

        JPQLQuery<Reply> jpqlQuery = from(qReply);

        jpqlQuery.select(qReply);

        jpqlQuery.where(qReply.movie.mno.eq(mno));

        // rno 기준 정렬
        PathBuilder<Reply> pathbuilder = new PathBuilder<Reply>(Reply.class,"reply");
        jpqlQuery.orderBy(new OrderSpecifier(Order.DESC,pathbuilder.get("rno")));

        return jpqlQuery.fetch();
    }
}
