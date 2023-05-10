package com.yoo.basicBoot.contorller.movie;

import com.yoo.basicBoot.dto.movie.ReplyDTO;
import com.yoo.basicBoot.security.dto.MemberAuthDTO;
import com.yoo.basicBoot.service.movie.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie/replies")
@RequiredArgsConstructor
@Log4j2
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/{mno}")
    public ResponseEntity<List<ReplyDTO>> getReplyList(@PathVariable Long mno, @AuthenticationPrincipal MemberAuthDTO memberAuthDTO){
        return ResponseEntity.ok(replyService.getReplyList(mno, memberAuthDTO));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Long> insetReply(@RequestBody ReplyDTO replyDTO, @AuthenticationPrincipal MemberAuthDTO memberAuthDTO){
        Long insertReply = replyService.insertReply(replyDTO, memberAuthDTO);
        return ResponseEntity.ok(insertReply);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> deleteReply(@PathVariable Long rno){
        replyService.deleteReply(rno);
        return ResponseEntity.ok("Success");
    }
}
