package com.GuardiansOfHoneyfruit.project.domain.region.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PnuCachingDto {

    private String fullPnuCd;
    private String frontPnuCd;

    public PnuCachingDto(String pnuCd){
        this.fullPnuCd = pnuCd;
        this.frontPnuCd = pnuCd;
    }


}
