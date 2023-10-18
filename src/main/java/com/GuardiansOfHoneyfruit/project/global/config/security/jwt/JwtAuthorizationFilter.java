package com.GuardiansOfHoneyfruit.project.global.config.security.jwt;

import com.GuardiansOfHoneyfruit.project.domain.user.domain.User;
import com.GuardiansOfHoneyfruit.project.global.config.security.oauth.OAuth2UserAttributeCreator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.resolveToken(request);

        try {
            if (accessToken != null) {
                accessToken = jwtTokenProvider.validateToken(accessToken, request, response); // 유효성 검사 실패시 null
                if (accessToken != null) {
                    User user = jwtTokenProvider.getMemberOfToken(accessToken);
                    saveMemberInSecurityContextHolder(user);
                }
            }
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    private void saveMemberInSecurityContextHolder(User user) {
        Map<String, Object> attributes = OAuth2UserAttributeCreator.createAttribute(user);

        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())),
                attributes, "id"
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                oAuth2User, null, oAuth2User.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
