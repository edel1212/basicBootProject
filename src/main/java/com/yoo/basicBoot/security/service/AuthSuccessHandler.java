package com.yoo.basicBoot.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoo.basicBoot.common.ResultState;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@Log4j2
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Custom Success Handler!!");
        log.info("authentication :: {}",authentication.getAuthorities());

        ResultState resultState = new ResultState();
        resultState.setStateCd(200);
        resultState.setStateMsg("success");

        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(objectMapper.writeValueAsString(resultState));

    }
}
