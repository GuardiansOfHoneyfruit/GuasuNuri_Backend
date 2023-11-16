package com.GuardiansOfHoneyfruit.project.domain.region.dto;

import com.GuardiansOfHoneyfruit.project.domain.region.domain.Danger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DangerResponse {

    private String regionName;
    private String regionCode;
    private String degreeName;
    private Double dangerLevel;
    private List<String> toolTip;

    public static DangerResponse from(Danger danger, RiskConversionResponse conversionResponse){
        return new DangerResponse(danger, conversionResponse.getRegionName(), conversionResponse.getRegionCode());
    }

    private DangerResponse(Danger danger, String regionName, String regionCode){
        this.regionCode = regionCode;
        this.regionName = regionName;
        this.dangerLevel = danger.getConvertedDegree();
        this.toolTip = danger.getToolTip();
        this.degreeName = danger.getDegreeName();
    }

}
