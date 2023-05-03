package com.yoo.basicBoot.repository.movie;

import com.yoo.basicBoot.entity.movie.Reply;
import com.yoo.basicBoot.repository.movie.support.SearchReplyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply,Long>, SearchReplyRepository {
}
