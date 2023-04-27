package com.yoo.basicBoot.dto.movie;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class MoviePageResultDTO<DTO, EN> {
    private List<DTO> dtoList;

    private int totalPage;

    private int page;
    private int size;

    private int start;

    private int end;

    boolean prev;
    boolean next;

    private List<Integer> pageList;

    // 생성자
    public MoviePageResultDTO(Page<EN> result, Function<EN, DTO> fn){
        this.dtoList = (List<DTO>) result.stream().collect(Collectors.toList());

        this.totalPage = result.getTotalPages();

        // MakePageResult
        this.makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int) Math.ceil(this.page / 10.0) * this.size;

        this.start = tempEnd - (this.size - 1);

        this.prev = start > 1;
        this.next =  this.totalPage > tempEnd;

        this.end = Math.min(this.totalPage, tempEnd);

        this.pageList = IntStream.rangeClosed(this.start, this.end).boxed()
                .collect(Collectors.toList());
    }

}
