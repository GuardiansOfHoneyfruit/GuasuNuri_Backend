package com.GuardiansOfHoneyfruit.project.domain.region.domain;

import com.GuardiansOfHoneyfruit.project.domain.observatory.domain.Observatory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "region")
@Entity
public class Region {

    @Id
    @Column(name = "pnu_cd", nullable = false)
    @Size(min = 10, max = 10, message = "지역 pnuId는 반드시 10자여야 합니다.")
    private String regionId;

    @ManyToOne
    @JoinColumn(name = "observatory", updatable = false)
    private Observatory observatory;

    @Column(name = "region_nm", nullable = false)
    private String regionName;

    @Builder
    public Region(String regionId, String regionName) {
        this.regionId = regionId;
        this.regionName = regionName;
    }

    public String getFirstFive() {
        return regionId.substring(0, 5);
    }

    public String getLastFive() {
        return regionId.substring(5);
    }

}
