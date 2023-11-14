package com.GuardiansOfHoneyfruit.project.domain.region.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiskConversionResponse {

    private String regionName;
    private String regionCode;
    private int dangerLevel;

    public RiskConversionResponse(String regionCode, String regionName, int dangerLevel){
        this.regionCode = regionCode;
        this.regionName = regionName;
        this.dangerLevel = dangerLevel;
    }

}
