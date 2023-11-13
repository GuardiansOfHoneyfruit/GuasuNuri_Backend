package com.GuardiansOfHoneyfruit.project.domain.region.dto;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RiskConversionRequest {

    @Valid private String regionCode;
    @Valid private String regionName;
    @Valid private String time;
    @Valid private Double avgTemperature;
    @Valid private Double minTemperature;
    @Valid private Double maxTemperature;
    @Valid private Double rainDay;
    @Valid private Double maxWindSpeed;
    @Valid private Double avgWindSpeed;
    @Valid private Double windDirectionMax;
    @Valid private Double avgHumidity;
    @Valid private Double solarRadiation;
    @Valid private Double avgTotalCloudAmount;
    @Valid private Double avgGroundTemperature;

}
