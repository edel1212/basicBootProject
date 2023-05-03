package com.yoo.basicBoot.config;

import com.yoo.basicBoot.security.MemberDetailService;
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

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private MemberDetailService memberDetailsService;

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

        httpSecurity.formLogin();

        httpSecurity.csrf().disable();
        return httpSecurity.build();
    }

}
