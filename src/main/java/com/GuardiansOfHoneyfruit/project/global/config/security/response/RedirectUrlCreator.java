package com.GuardiansOfHoneyfruit.project.global.config.security.response;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class RedirectUrlCreator {

    @Value("${spring.jwt.redirect-url}")
    private String url;
    private static final String USER_TOKEN_ATTRIBUTE_NAME = "accessToken";
    private static final String USER_REGION_ATTRIBUTE_NAME = "region";
    private static final String USER_UUID_ATTRIBUTE_NAME = "userUuid";
    private static final String EXCEPTION_ATTRIBUTE_NAME = "exception";

    public String createTargetUrl(HttpServletRequest request) {
        String redirectUrl = url;
        String exception = (String) request.getAttribute(EXCEPTION_ATTRIBUTE_NAME);
        redirectUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam(EXCEPTION_ATTRIBUTE_NAME, exception)
                .build().toUriString();

        return redirectUrl;
    }

    public String createTargetUrl(String accessToken, String userUuid, boolean isRegionNull) throws IOException {
        String redirectUrl = url;
        redirectUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam(USER_TOKEN_ATTRIBUTE_NAME, accessToken)
                .queryParam(USER_REGION_ATTRIBUTE_NAME, isRegionNull)
                .queryParam(USER_UUID_ATTRIBUTE_NAME, userUuid)
                .build().toUriString();

        return redirectUrl;
    }
}
