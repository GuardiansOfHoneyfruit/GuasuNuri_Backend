package com.GuardiansOfHoneyfruit.project.domain.region.dto;

import com.GuardiansOfHoneyfruit.project.domain.asos.dto.AsosEntityDto;
import com.GuardiansOfHoneyfruit.project.domain.region.domain.Region;
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
    @Valid private Double avgTemperature;
    @Valid private Double maxWindSpeed;
    @Valid private Double avgWindSpeed;
    @Valid private Double avgHumidity;
    @Valid private Double avgTotalCloudAmount;
    @Valid private Double avgGroundTemperature;
    @Valid private Double avgDewPointTemperature;
    @Valid private Double avgWaterVaporPressure;

    public static RiskConversionRequest fromAsosEntity(Region region, AsosEntityDto asosDto) {
        return new RiskConversionRequest(
                region.getRegionCode(),
                region.getRegionName(),
                asosDto.getAvgTemperature(),
                asosDto.getMaxWindSpeed(),
                asosDto.getAvgWindSpeed(),
                asosDto.getAvgHumidity(),
                asosDto.getAvgTotalCloudAmount(),
                asosDto.getAvgGroundTemperature(),
                asosDto.getAvgDewPointTemperature(),
                asosDto.getAvgWaterVaporPressure()
        );
    }

    @Override
    public String toString(){
        return this.regionCode;
    }

}
