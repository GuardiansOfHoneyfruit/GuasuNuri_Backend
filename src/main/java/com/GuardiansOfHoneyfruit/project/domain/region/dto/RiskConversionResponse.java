package com.GuardiansOfHoneyfruit.project.domain.region.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiskConversionResponse {

    private String regionName;
    private String regionCode;
    private int riskOfDegree;

    public RiskConversionResponse(String regionCode, String regionName, int riskOfDegree){
        this.regionCode = regionCode;
        this.regionName = regionName;
        this.riskOfDegree = riskOfDegree;
    }

}
