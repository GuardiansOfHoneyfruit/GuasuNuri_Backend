package com.GuardiansOfHoneyfruit.project.domain.observatory.domain;

import com.GuardiansOfHoneyfruit.project.domain.asos.domain.Asos;
import com.GuardiansOfHoneyfruit.project.domain.asos.dto.AsosEntityDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wt_observatory")
public class Observatory {

    @Id
    @Column(name = "observatory_cd" )
    private Long observatoryId;

    @Column(name = "observatory_nm", nullable = false, updatable = false, unique = true)
    private String observatoryName;

    @OneToMany(mappedBy = "observatory", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Asos> asos = new ArrayList<>();

    @Builder
    public Observatory(Long observatoryId, String observatoryName){
        this.observatoryId = observatoryId;
        this.observatoryName = observatoryName;
    }

    public void addAsos(AsosEntityDto dto) {
        this.asos.add(buildAsos(dto));
    }

    private Asos buildAsos(AsosEntityDto dto){
        return Asos.builder()
                .observatory(this)
                .avgTemperature(dto.getAvgTemperature())
                .minTemperature(dto.getMinTemperature())
                .time(dto.getTime())
                .avgHumidity(dto.getAvgHumidity())
                .maxTemperature(dto.getMaxTemperature())
                .avgGroundTemperature(dto.getAvgGroundTemperature())
                .avgTotalCloudAmount(dto.getAvgTotalCloudAmount())
                .rainDay(dto.getRainDay())
                .maxWindSpeed(dto.getMaxWindSpeed())
                .avgWindSpeed(dto.getAvgWindSpeed())
                .windDirectionMax(dto.getWindDirectionMax())
                .solarRadiation(dto.getSolarRadiation())
                .build();
    }
}