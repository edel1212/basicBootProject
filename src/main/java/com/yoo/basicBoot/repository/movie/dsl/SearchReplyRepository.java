package com.yoo.basicBoot.repository.movie.dsl;

import com.yoo.basicBoot.entity.movie.Reply;

import java.util.List;

public interface SearchReplyRepository {
    List<Reply> getReplyList(Long mno);
}
