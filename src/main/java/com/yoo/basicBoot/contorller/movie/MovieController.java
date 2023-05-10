package com.yoo.basicBoot.contorller.movie;

import com.yoo.basicBoot.dto.movie.MovieDTO;
import com.yoo.basicBoot.dto.movie.MoviePageRequestDTO;
import com.yoo.basicBoot.dto.movie.MoviePageResultDTO;
import com.yoo.basicBoot.security.dto.MemberAuthDTO;
import com.yoo.basicBoot.service.movie.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movie")
@RequiredArgsConstructor
@Log4j2
public class MovieController {

    private final MovieService movieService;

    @PreAuthorize("permitAll()")
    @GetMapping("/list")
    public void movieList(){}


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public void movieRegister(){}

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Long> insertMovie(@RequestBody MovieDTO movieDTO, @AuthenticationPrincipal MemberAuthDTO memberAuthDTO){
        if(movieDTO.getMno() != null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0L);

        long mno = movieService.insertMovie(movieDTO, memberAuthDTO);

        return ResponseEntity.ok(mno);
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity<MoviePageResultDTO> getList(MoviePageRequestDTO moviePageRequestDTO){
        return ResponseEntity.ok(movieService.getMovieList(moviePageRequestDTO));
    }

    @ResponseBody
    @GetMapping("/{mno}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable Long mno){
        MovieDTO result = movieService.getMovieDetail(mno);
        if(result == null || result.getMno() == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<MovieDTO>> getApiLastMovieList(){
     //  WebClient
        return null;
    }

}
