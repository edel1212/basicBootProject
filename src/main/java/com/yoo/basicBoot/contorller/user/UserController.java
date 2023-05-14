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

    @GetMapping("/check")
    public String send(String email, String uuid, RedirectAttributes redirectAttributes){
        boolean result = memberService.checkVerification(email,uuid);
        String msg = result ? "인증에 성공하였습니다." : "기간이 만료된 인증번호입니다. 재전송 해드리겠습니다.";
        redirectAttributes.addFlashAttribute("msg",msg);
        return "redirect:/user/login";
    }

}
