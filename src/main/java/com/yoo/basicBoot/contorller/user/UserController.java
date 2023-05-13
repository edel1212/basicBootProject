package com.yoo.basicBoot.contorller.user;

import com.yoo.basicBoot.common.ResultState;
import com.yoo.basicBoot.dto.user.MemberDTO;
import com.yoo.basicBoot.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    // Member
    private final MemberService memberService;

    @GetMapping("/login")
    public void loginPage(){}

    @GetMapping("/register")
    public void register(){}


    @GetMapping()
    @ResponseBody
    public ResponseEntity<Long> findMember(String email){
        return ResponseEntity.ok(memberService.findMemberByEmail(email));
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<ResultState> register(@RequestBody MemberDTO memberDTO){
        ResultState resultSate = new ResultState();
        String email = memberService.registerMember(memberDTO);
        if(email == null){
            resultSate.setStateCd(999);
            resultSate.setStateMsg(null);
        }else{
            resultSate.setStateCd(200);
            resultSate.setStateMsg(email);
        }
        return ResponseEntity.ok(resultSate);
    }

    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<ResultState> send(@RequestBody MemberDTO memberDTO){
        memberService.sendVerificationMail(memberDTO);
        ResultState resultState = ResultState.builder()
                .stateCd(200)
                .stateMsg("success").build();
        return ResponseEntity.ok(resultState);
    }

}
