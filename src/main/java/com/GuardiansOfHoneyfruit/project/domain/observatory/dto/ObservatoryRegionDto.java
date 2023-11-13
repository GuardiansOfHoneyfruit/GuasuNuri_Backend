package com.GuardiansOfHoneyfruit.project.domain.observatory.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ObservatoryRegionDto {

    private String regionName;
    private String regionCode;
    private Long observatoryCode;
    private String observatoryName;

    @Builder
    public ObservatoryRegionDto(String regionName, String regionCode, Long observatoryCode, String observatoryName){
        this.regionCode = regionCode;
        this.regionName = regionName;
        this.observatoryCode = observatoryCode;
        this.observatoryName = observatoryName;
    }

}
