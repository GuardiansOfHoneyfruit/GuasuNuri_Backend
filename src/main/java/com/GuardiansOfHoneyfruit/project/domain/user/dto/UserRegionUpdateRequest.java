package com.GuardiansOfHoneyfruit.project.domain.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRegionUpdateRequest {

    private String regionCode;

    private UserRegionUpdateRequest(String regionCode){
        this.regionCode = regionCode;
    }

}
