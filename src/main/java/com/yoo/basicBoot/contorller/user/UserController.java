package com.yoo.basicBoot.contorller.user;

import com.yoo.basicBoot.common.ResultState;
import com.yoo.basicBoot.common.redis.RedisUtil;
import com.yoo.basicBoot.dto.user.MemberDTO;
import com.yoo.basicBoot.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    // Member
    private final MemberService memberService;

    // Mail
    private final JavaMailSender javaMailSender;

    // Redis
    private final RedisUtil redisUtil;

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
    public ResponseEntity<Boolean> send(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        String testCode = "axm349vm1asxzc";

        simpleMailMessage.setTo("edel1212@naver.com");
        simpleMailMessage.setSubject("인증번호 입니다.");
        simpleMailMessage.setText(testCode+"입니다");
        javaMailSender.send(simpleMailMessage);
        redisUtil.setDataExpire("edel1212@naver.com", testCode, 30);
        return ResponseEntity.ok(true);
    }

}
