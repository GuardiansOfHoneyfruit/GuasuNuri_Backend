package com.GuardiansOfHoneyfruit.project.global.config.security.jwt;

import com.GuardiansOfHoneyfruit.project.domain.user.dao.UserFindDao;
import com.GuardiansOfHoneyfruit.project.domain.user.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final RedisTemplate<String, String> redisTokenTemplate;
    private final UserFindDao userFindDao;


    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.token.access-expiration-time}")
    private long accessTokenExpirationTime;

    @Value("${spring.jwt.token.refresh-expiration-time}")
    private long refreshTokenExpirationTime;

    public String createAccessToken(OAuth2User oAuth2User){
        return JWT.create()
                .withSubject("Access")
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpirationTime))
                .withClaim("userUuid", (String) oAuth2User.getAttributes().get("userUuid"))
                .withClaim("role", (String) oAuth2User.getAttributes().get("role"))
                .sign(Algorithm.HMAC512(secretKey));
    }
    public String createAccessToken(User user){
        return JWT.create()
                .withSubject("Access")
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpirationTime))
                .withClaim("userUuid", user.getUserUuid())
                .withClaim("role", user.getRole())
                .sign(Algorithm.HMAC512(secretKey));
    }

    public void createRefreshToken(OAuth2User oAuth2User, String accessToken, HttpServletRequest request){
        String refreshToken = JWT.create()
                .withSubject("Access")
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
                .withClaim("userId", (String) oAuth2User.getAttributes().get("userUuid"))
                .withClaim("role", (String) oAuth2User.getAttributes().get("role"))
                .sign(Algorithm.HMAC512(secretKey));
        try {
            // redis에 저장
            redisTokenTemplate.opsForValue().set(
                    accessToken,
                    refreshToken,
                    refreshTokenExpirationTime,
                    TimeUnit.MILLISECONDS
            );
        } catch (RedisConnectionFailureException e) {
            request.setAttribute("exception", e);
        }

    }

    public String createNewAccessToken(String accessToken, HttpServletRequest request) {
        String refreshToken = redisTokenTemplate.opsForValue().get(accessToken);
        if (refreshToken != null) {

            User user = getMemberOfToken(accessToken);
            String newAccessToken = createAccessToken(user);

            try {
                // redis 저장
                redisTokenTemplate.opsForValue().set(
                        newAccessToken,
                        refreshToken,
                        accessTokenExpirationTime,
                        TimeUnit.MILLISECONDS
                );
            } catch (Exception e) {
                request.setAttribute("exception", e);
            }

            return newAccessToken;
        }
        return null;
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;

    }

    public String validateToken(String accessToken, HttpServletRequest request, HttpServletResponse response){
        try{

            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(accessToken);
            return accessToken;

        } catch(TokenExpiredException e) {

            String newAccessToken = createNewAccessToken(accessToken, request);
            if (newAccessToken != null) {
                response.setHeader("newAccessToken", newAccessToken); // 클라이언트에 새로운 accessToken 발급
                return newAccessToken;
            }
            request.setAttribute("exception", e);

        } catch(JWTDecodeException e) {

            request.setAttribute("exception", e);

        } catch (SignatureVerificationException e) {
            request.setAttribute("exception", e);
        }
        return null;
    }

    public User getMemberOfToken(String accessToken) {
        String userUuid = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(accessToken).getClaim("userUuid").asString();
        User user = userFindDao.findByUserUuId(userUuid);
        return user;
    }

}
