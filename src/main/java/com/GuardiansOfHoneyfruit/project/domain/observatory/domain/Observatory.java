package com.GuardiansOfHoneyfruit.project.domain.observatory.domain;

import com.GuardiansOfHoneyfruit.project.domain.asos.domain.Asos;
import com.GuardiansOfHoneyfruit.project.domain.region.domain.Region;
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
    private List<Region> regionList = new ArrayList<>();

    @OneToMany(mappedBy = "observatory", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Asos> asos = new ArrayList<>();

    @Builder
    public Observatory(Long observatoryId, String observatoryName){
        this.observatoryId = observatoryId;
        this.observatoryName = observatoryName;
    }

//    private Asos buildAsos(Observatory observatory, String time, Double avgTemperature, Double minTemperature, Double maxTemperature, Double rainDay, Double maxWindSpeed, Double avgWindSpeed, Double windDirectionMax, Double avgHumidity, Double solarRadiation, Double avgTotalCloudAmount, Double avgGroundTemperature){
//        return Asos.builder()
//                .
//    }

}