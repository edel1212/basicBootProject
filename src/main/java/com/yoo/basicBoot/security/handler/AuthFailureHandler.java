package com.yoo.basicBoot.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoo.basicBoot.common.ResultState;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Log4j2
public class AuthFailureHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("Custom FailureHandler.");

        ResultState resultState = new ResultState();
        resultState.setStateCd(999);
        if( "uuidCheck".equals(exception.getMessage()) ){
            resultState.setStateMsg("이메일 인증이 되어있지 않은 계정입니다.");
        } else if( "pwCount".equals(exception.getMessage()) ){
            resultState.setStateMsg("잠긴 계정입니다. 비밀번호 재설정을 해주세요");
        } else{
            resultState.setStateMsg("아이디 혹은 비밀번호를 체크 해주세요.");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(objectMapper.writeValueAsString(resultState));

    }
}
