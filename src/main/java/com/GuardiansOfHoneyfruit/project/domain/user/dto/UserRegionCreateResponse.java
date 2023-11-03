package com.GuardiansOfHoneyfruit.project.domain.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRegionCreateResponse {

    private String userUuid;

    private UserRegionCreateResponse(String userUuid){
        this.userUuid = userUuid;
    }

    public static UserRegionCreateResponse fromUserUuid(String userUuid){
        return new UserRegionCreateResponse(userUuid);
    }

}
