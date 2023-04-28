package com.yoo.basicBoot.contorller.movie;

import com.yoo.basicBoot.dto.movie.ReplyDTO;
import com.yoo.basicBoot.service.movie.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/{mno}")
    public ResponseEntity<List<ReplyDTO>> getReplyList(@PathVariable Long mno){
        return ResponseEntity.ok(replyService.getReplyList(mno));
    }
}
