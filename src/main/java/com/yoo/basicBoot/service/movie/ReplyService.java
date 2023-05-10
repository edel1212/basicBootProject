package com.yoo.basicBoot.service.movie;

import com.yoo.basicBoot.dto.movie.ReplyDTO;
import com.yoo.basicBoot.entity.movie.Movie;
import com.yoo.basicBoot.entity.movie.Reply;
import com.yoo.basicBoot.entity.user.Member;
import com.yoo.basicBoot.security.dto.MemberAuthDTO;

import java.util.List;

public interface ReplyService {

    List<ReplyDTO> getReplyList(Long mno,MemberAuthDTO memberAuthDTO);

    Long insertReply(ReplyDTO replyDTO, MemberAuthDTO memberAuthDTO);

    void deleteReply(Long rno);

    default ReplyDTO entityToDto(Reply reply,String loginUserId){
        return ReplyDTO.builder()
                .rno(reply.getRno())
                .mno(reply.getMovie().getMno())
                .text(reply.getText())
                .replier(reply.getMember().getName())
                .replierId(reply.getMember().getEmail())
                .identityFlag(reply.getMember().getEmail().equals(loginUserId))
                .modDate(reply.getModDate())
                .regDate(reply.getRegDate())
                .build();
    }

    default Reply dtoToEntity(ReplyDTO  replyDTO){
        return Reply.builder()
                .rno(replyDTO.getRno())
                .movie(Movie.builder().mno(replyDTO.getMno()).build())
                .text(replyDTO.getText())
                .member(Member.builder().email(replyDTO.getReplierId()).build())
                .build();
    }

}
