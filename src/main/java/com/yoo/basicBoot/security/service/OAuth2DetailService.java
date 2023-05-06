package com.yoo.basicBoot.security.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * <p>OAuth 로그인 시 사용 되는 Service</p>
 * <p>Service지만 Interface가 아니며 DefaultOAuth2UserService를 상속받아</p>
 * <p>OAuth2User를 @Override 하여 구현중</p>
 * */
@Service
@Log4j2
public class OAuth2DetailService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // OAuth2 사용된 API
        String clientName =  userRequest.getClientRegistration().getClientName();

        // 사용자 정보
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return oAuth2User;
    }
}
