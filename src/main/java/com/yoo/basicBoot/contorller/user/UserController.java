package com.yoo.basicBoot.contorller.user;

import com.yoo.basicBoot.dto.user.MemberDTO;
import com.yoo.basicBoot.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final MemberService memberService;

    @GetMapping("/login")
    public void loginPage(){}

    @GetMapping("/register")
    public void register(){}


    @GetMapping("/{email}")
    @ResponseBody
    public ResponseEntity<Long> findMember(@PathVariable String email){
        return ResponseEntity.ok(memberService.findMemberByEmail(email));
    }

}
