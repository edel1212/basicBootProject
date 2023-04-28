package com.yoo.basicBoot.service.movie;

import com.yoo.basicBoot.dto.movie.ReplyDTO;
import com.yoo.basicBoot.repository.movie.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyListServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    @Override
    public List<ReplyDTO> getReplyList(Long mno) {
        return replyRepository.getReplyList(mno).stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
