package com.GuardiansOfHoneyfruit.project.global.config.security.oauth;

import com.GuardiansOfHoneyfruit.project.domain.user.dao.UserFindDao;
import com.GuardiansOfHoneyfruit.project.domain.user.domain.User;
import com.GuardiansOfHoneyfruit.project.domain.user.service.UserCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserFindDao userFindDao;
    private final UserCreateService userCreateService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String userEmail = getEmail(userRequest);
        Optional<User> optionalUser = userFindDao.findOptionalUserByEmail(userEmail);
        User user;
        if (optionalUser.isEmpty()){
            user = userCreateService.createUser(userEmail);
        } else {
            user = optionalUser.get();
        }
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRole())), OAuth2UserAttributeCreator.createAttribute(user), "userUuid");
    }

    private String getEmail(OAuth2UserRequest userRequest){
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> kakao_account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
        return (String) kakao_account.get("email");
    }

}
