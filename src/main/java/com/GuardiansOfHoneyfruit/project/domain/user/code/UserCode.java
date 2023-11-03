package com.GuardiansOfHoneyfruit.project.domain.user.code;

import com.GuardiansOfHoneyfruit.project.global.common.code.ResponseCode;
import org.springframework.http.HttpStatus;

public enum UserCode implements ResponseCode {
    USER_REGION_SETTING_SUCCESS("지역 설정에 성공하였습니다.", 201, HttpStatus.CREATED),
    USER_REGION_UPDATE_SUCCESS( "지역 업데이트에 성공하였습니다.", 204,  HttpStatus.NO_CONTENT);

    private final int code;
    private final String message;

    private final HttpStatus httpStatus;

    @Override
    public int getCode(){return this.code;}

    @Override
    public String getMessage(){return this.message;};

    @Override
    public HttpStatus getHttpStatus(){return this.httpStatus;};
    UserCode(String message, int code, HttpStatus httpStatus){
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
