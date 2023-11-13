package com.GuardiansOfHoneyfruit.project.domain.region.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionNameDto {

    private String regionName;
    private String regionCode;

    public RegionNameDto(String regionName, String regionCode){
        this.regionName = regionName;
        this.regionCode = regionCode;
    }

}
