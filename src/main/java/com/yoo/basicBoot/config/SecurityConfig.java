package com.yoo.basicBoot.config;

import com.yoo.basicBoot.security.filter.OAuth2LoginFilter;
import com.yoo.basicBoot.security.handler.AuthFailureHandler;
import com.yoo.basicBoot.security.handler.AuthSuccessHandler;
import com.yoo.basicBoot.security.service.MemberDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private MemberDetailService memberDetailsService;

    public OAuth2LoginFilter oAuth2LoginFilter(AuthenticationManager authenticationManager) throws Exception{
        OAuth2LoginFilter oAuth2LoginFilter = new OAuth2LoginFilter("/api/login" );
        oAuth2LoginFilter.setAuthenticationManager(authenticationManager);
        return oAuth2LoginFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Security Config PasswordEncoder!");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        // AuthenticationManager 객체 생성 내가 사용할 UserDetail, PasswordEncoder 지정
        AuthenticationManager authenticationManager = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(memberDetailsService)
                .passwordEncoder(this.passwordEncoder())
                .and()
                .build();
        // 앞서 생성한 AuthenticationManager 주입
        httpSecurity.authenticationManager(authenticationManager);

        httpSecurity
                .formLogin()                                // 로그인 페이지 지정s
                .loginPage("/user/login")                   // login URL
                .loginProcessingUrl("/user/login")          // Action URI
                .successHandler(new AuthSuccessHandler())   // CustomSuccess Handler
                .failureHandler(new AuthFailureHandler());

        // remember me
        httpSecurity.rememberMe().tokenValiditySeconds(60*60*24*7);

        // OAuth2 login
        httpSecurity.oauth2Login();

        // 로그인 필터 OAuth일 경우 감지를 위함
        httpSecurity.addFilterBefore(oAuth2LoginFilter(authenticationManager)
                ,UsernamePasswordAuthenticationFilter.class);

        // 로그아웃
        httpSecurity.logout();

        httpSecurity.csrf().disable();
        return httpSecurity.build();
    }

}
