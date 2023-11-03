package com.GuardiansOfHoneyfruit.project.global.config.security.jwt;

import com.GuardiansOfHoneyfruit.project.global.config.security.response.JsonResponser;
import com.GuardiansOfHoneyfruit.project.global.error.exception.ErrorCode;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        Exception e = (Exception) request.getAttribute("exception");
        if (e instanceof JWTDecodeException) {
            JsonResponser.sendJsonResponse(response, ErrorCode.INVALID_TOKEN);
        } else if (e instanceof RedisConnectionFailureException) {
            JsonResponser.sendJsonResponse(response, ErrorCode.UNCONNECTED_REDIS);
        } else if (e instanceof TokenExpiredException) {
            JsonResponser.sendJsonResponse(response, ErrorCode.EXPIRED_TOKEN);
        } else if (e instanceof SignatureVerificationException){
            JsonResponser.sendJsonResponse(response, ErrorCode.INVALID_TOKEN);
        } else {
            JsonResponser.sendJsonResponse(response, ErrorCode.UNAUTHORIZATION);
        }
    }
}
