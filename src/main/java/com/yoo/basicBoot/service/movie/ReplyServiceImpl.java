package com.yoo.basicBoot.service.movie;

import com.yoo.basicBoot.dto.movie.ReplyDTO;
import com.yoo.basicBoot.entity.movie.Reply;
import com.yoo.basicBoot.repository.movie.ReplyRepository;
import com.yoo.basicBoot.security.dto.MemberAuthDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    @Override
    public List<ReplyDTO> getReplyList(Long mno,MemberAuthDTO memberAuthDTO) {
        String loginUserId;
        if(memberAuthDTO != null){
            loginUserId = memberAuthDTO.getEmail() ;
        } else {
            loginUserId = "";
        }// if - else
        return replyRepository.getReplyList(mno).stream()
                .map(data->{
                    return this.entityToDto(data, loginUserId);
                }).collect(Collectors.toList());
    }

    @Override
    public Long insertReply(ReplyDTO replyDTO, MemberAuthDTO memberAuthDTO) {
        replyDTO.setReplierId(memberAuthDTO.getEmail());
        Reply reply = this.dtoToEntity(replyDTO);
        replyRepository.save(reply);
        return replyDTO.getMno();
    }

    @Override
    public void deleteReply(Long rno){
        replyRepository.deleteById(rno);
    }

}
