package com.yoo.basicBoot.security.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>OAuth 로그인 성공 시 사용될 Class<p/>
 * <p>SecurityConfig에서 Filter 순서를 지정해줌<p/>
 * <p>
 *     <strong>
 *         API 로그인 경우 Fail의 경우 자동으로 튕겨내주기 때문애 </br>
 *         성공 로직만 신경쓰면 된다.
 *     </strong>
 * </p>
 * */
@Log4j2
public class OAuth2LoginFilter extends AbstractAuthenticationProcessingFilter {
    public OAuth2LoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("OAuth Login Filter is catching");

        //
        String email = request.getParameter("email");

        if(email == null) throw new BadCredentialsException("email can not be null");

        return null;
    }
}
