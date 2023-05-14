package com.yoo.basicBoot.contorller.user;

import com.yoo.basicBoot.common.ResultState;
import com.yoo.basicBoot.dto.user.MemberDTO;
import com.yoo.basicBoot.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    // Member
    private final MemberService memberService;

    @GetMapping("/login")
    public void loginPage(@ModelAttribute("msg") String msg){
        log.info("----------------------------");
        log.info(msg);
        log.info("----------------------------");
    }

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

    @PutMapping("/auth")
    @ResponseBody
    public ResponseEntity<ResultState> send(@RequestBody MemberDTO memberDTO){
        String email = memberDTO.getEmail();
        String uuid = memberDTO.getUuid();
        boolean result = memberService.checkVerification(email,uuid);
        ResultState resultState = ResultState.builder().build();
        if(!result){
            resultState.setStateCd(999);
            resultState.setStateMsg("기간이 만료된 인증번호입니다. 다시 전송해드렸습니다.");
            boolean sendVerificationMail =  memberService.sendVerificationMail(memberDTO);
            if(!sendVerificationMail) resultState.setStateMsg("잘못된 접근입니다 다시 확인해주세요.");
            return ResponseEntity.ok(resultState);
        }
        resultState.setStateCd(200);
        resultState.setStateMsg("인증에 성공하였습니다. 로그인을 해주세요.");
        return ResponseEntity.ok(resultState);
    }

}
