package com.GuardiansOfHoneyfruit.project.domain.region.domain;

import com.GuardiansOfHoneyfruit.project.domain.soil.domain.Soil;
import com.GuardiansOfHoneyfruit.project.domain.soil.dto.SoilResponseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PNU")
@Entity
public class Pnu {

    @Id
    @Column(name = "PNU_CD", nullable = false)
    @Size(min = 10, max = 10, message = "지역 pnuId는 반드시 10자여야 합니다.")
    private String pnuCode;

    @Column(name = "PNU_ADDRESS", nullable = false)
    private String pnuAddress;

    @OneToMany(mappedBy = "pnu", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Soil> soilData = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "REGION_CD", nullable = false)
    private Region region;

    @Builder
    public Pnu(final Region region, final String pnuCode, String pnuAddress) {
        this.region = region;
        this.pnuCode = pnuCode;
        this.pnuAddress = pnuAddress;
    }

    public Soil addSoil(SoilResponseDto dto){
        Soil soil = buildSoil(dto);
        this.soilData.add(soil);
        return soil;
    }

    public Soil buildSoil(SoilResponseDto dto){
        return dto.toEntity(this);
    }

}
