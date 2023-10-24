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

    public String createTargetUrl(HttpServletRequest request) {
        String redirectUrl = url;
        String exception = (String) request.getAttribute("exception");
        redirectUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("exception", exception)
                .build().toUriString();

        return redirectUrl;
    }

    public String createTargetUrl(String accessToken, String userId) throws IOException {
        String redirectUrl = url;
        redirectUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("accessToken", accessToken)
                .queryParam("userId", userId)
                .build().toUriString();

        return redirectUrl;
    }
}
