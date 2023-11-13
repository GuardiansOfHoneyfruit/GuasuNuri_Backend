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

    public static RiskConversionRequest fromAsosEntity(Region region, AsosEntityDto asosDto) {
        return new RiskConversionRequest(
                region.getRegionCode(),
                region.getRegionName(),
                asosDto.getTime(),
                asosDto.getAvgTemperature(),
                asosDto.getMinTemperature(),
                asosDto.getMaxTemperature(),
                asosDto.getRainDay(),
                asosDto.getMaxWindSpeed(),
                asosDto.getAvgWindSpeed(),
                asosDto.getWindDirectionMax(),
                asosDto.getAvgHumidity(),
                asosDto.getSolarRadiation(),
                asosDto.getAvgTotalCloudAmount(),
                asosDto.getAvgGroundTemperature()
        );
    }


}
