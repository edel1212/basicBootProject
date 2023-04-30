package com.yoo.basicBoot.service.movie;

import com.yoo.basicBoot.dto.movie.ReplyDTO;
import com.yoo.basicBoot.entity.movie.Movie;
import com.yoo.basicBoot.entity.movie.Reply;

import java.util.List;

public interface ReplyService {

    List<ReplyDTO> getReplyList(Long mno);

    Long insertReply(ReplyDTO replyDTO);

    void deleteReply(Long rno);

    default ReplyDTO entityToDto(Reply reply){
        return ReplyDTO.builder()
                .rno(reply.getRno())
                .mno(reply.getMovie().getMno())
                .text(reply.getText())
                .replier(reply.getReplier())
                .modDate(reply.getModDate())
                .regDate(reply.getRegDate())
                .build();
    }

    default Reply dtoToEntity(ReplyDTO  replyDTO){
        return Reply.builder()
                .rno(replyDTO.getRno())
                .movie(Movie.builder().mno(replyDTO.getMno()).build())
                .text(replyDTO.getText())
                .replier(replyDTO.getReplier())
                .build();
    }

}
