package com.GuardiansOfHoneyfruit.project.global.config.security.response;

import com.GuardiansOfHoneyfruit.project.global.error.ErrorResponse;
import com.GuardiansOfHoneyfruit.project.global.error.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonResponser {
    public static void sendJsonResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json"); // JSON 형식의 데이터라고 설정
        response.setCharacterEncoding("UTF-8"); // 인코딩 설정
        response.setStatus(errorCode.getStatus());
        response.getWriter().write(new ObjectMapper().writeValueAsString(ErrorResponse.of(errorCode)));
    }
}
