package com.yoo.basicBoot.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class MoviePageRequestDTO {

    private int page;
    private int size;

    public MoviePageRequestDTO(){
        this.page = 1;
        this.size = 10;
    }

    public Pageable

    getPageable(Sort sort){
        return PageRequest.of(this.page -1, this.size , sort);
    }

}
