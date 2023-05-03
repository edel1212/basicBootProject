package com.yoo.basicBoot.repository;


import com.yoo.basicBoot.entity.movie.Movie;
import com.yoo.basicBoot.entity.movie.Reply;
import com.yoo.basicBoot.repository.movie.ReplyRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply(){
        IntStream.rangeClosed(1, 20).forEach(item->{
            Reply reply = Reply.builder()
                    .replier("edel-"+item)
                    .text(item+"번째 댓글입니다!!")
                    .movie(Movie.builder().mno(205L).build())
                    .build();
            replyRepository.save(reply);
            log.info(reply.getRno());
        });
    }
}
