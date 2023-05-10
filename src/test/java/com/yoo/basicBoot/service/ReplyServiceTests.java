package com.yoo.basicBoot.service;

import com.yoo.basicBoot.dto.movie.ReplyDTO;
import com.yoo.basicBoot.service.movie.ReplyService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {

    @Autowired
    private ReplyService replyService;

    @Test
    public void getReviewList(){
       List<ReplyDTO> result =  replyService.getReplyList(222L,null);
       log.info(Arrays.toString(result.toArray()));
    }

}
