package com.yoo.basicBoot.repository.movie;

import com.yoo.basicBoot.entity.movie.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
}
