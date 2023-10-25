package com.GuardiansOfHoneyfruit.project.domain.asos.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AsosEntityDto {
    private String time;
    private Double avgTemperature;
    private Double minTemperature;
    private Double maxTemperature;
    private Double rainDay;
    private Double maxWindSpeed;
    private Double avgWindSpeed;
    private Double windDirectionMax;
    private Double avgHumidity;
    private Double solarRadiation;
    private Double avgTotalCloudAmount;
    private Double avgGroundTemperature;
}
