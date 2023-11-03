package com.GuardiansOfHoneyfruit.project.global.config.security.oauth;

import com.GuardiansOfHoneyfruit.project.domain.user.dao.UserFindDao;
import com.GuardiansOfHoneyfruit.project.global.config.security.jwt.JwtTokenProvider;
import com.GuardiansOfHoneyfruit.project.global.config.security.response.RedirectUrlCreator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedirectUrlCreator redirectUrlCreator;
    private final UserFindDao userFindDao;
    private static final String USER_UUID_ATTRIBUTE_NAME = "userUuid";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // 이미 응답이 커밋됐는데 response하면 예외 발생하므로 return
        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // token 생성
        String accessToken = jwtTokenProvider.createAccessToken(oAuth2User);
        jwtTokenProvider.createRefreshToken(oAuth2User, accessToken, request);

        // token 발급 or 예외 리다이렉트
        String redirectUrl;
        if (request.getAttribute("exception") != null) {
            redirectUrl = redirectUrlCreator.createTargetUrl(request);
        } else {
            String userUuid = oAuth2User.getAttributes().get(USER_UUID_ATTRIBUTE_NAME).toString();
            redirectUrl = redirectUrlCreator.createTargetUrl(accessToken, userUuid, userFindDao.isUserRegionNull(userUuid));
        }

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
