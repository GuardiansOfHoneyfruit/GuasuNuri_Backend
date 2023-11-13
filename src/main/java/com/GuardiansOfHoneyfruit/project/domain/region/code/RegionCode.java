package com.GuardiansOfHoneyfruit.project.domain.region.code;

import com.GuardiansOfHoneyfruit.project.global.common.code.ResponseCode;
import org.springframework.http.HttpStatus;

public enum RegionCode implements ResponseCode {

    GET_ALL_REGION_NAME(200, "지역명 조회에 성공하였습니다.", HttpStatus.OK);

    private final int code;
    private final String message;

    private final HttpStatus httpStatus;

    RegionCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public HttpStatus getHttpStatus(){
        return this.httpStatus;
    };
}
