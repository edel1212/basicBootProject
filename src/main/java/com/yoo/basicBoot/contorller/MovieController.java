package com.yoo.basicBoot.contorller;

import com.yoo.basicBoot.dto.movie.MovieDTO;
import com.yoo.basicBoot.service.movie.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movie")
@RequiredArgsConstructor
@Log4j2
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/list")
    public void movieList(){}

    @GetMapping("/register")
    public void movieRegister(){}

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Long> insertMovie(@RequestBody MovieDTO movieDTO){
        log.info(movieDTO);

        if(movieDTO.getMno() != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0L);
        }

        long mno = movieService.insertMovie(movieDTO);

        return ResponseEntity.ok(mno);
    }

}
