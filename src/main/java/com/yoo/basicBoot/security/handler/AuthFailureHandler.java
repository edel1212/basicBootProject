package com.yoo.basicBoot.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoo.basicBoot.common.ResultState;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
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
        resultState.setStateMsg("fail");

        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(objectMapper.writeValueAsString(resultState));

    }
}
