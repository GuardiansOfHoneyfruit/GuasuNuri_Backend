package com.GuardiansOfHoneyfruit.project.domain.region.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegionCachingDto {

    private String fullPnuCd;
    private String frontPnuCd;

    public RegionCachingDto(String pnuCd){
        this.fullPnuCd = pnuCd;
        this.frontPnuCd = pnuCd;
    }


}
