package com.GuardiansOfHoneyfruit.project.domain.observatory.domain;

import com.GuardiansOfHoneyfruit.project.domain.region.domain.Region;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "observatory")
public class Observatory {

    @Id
    @Column(name = "observatory_cd" )
    private Long observatoryId;

    @Column(name = "observatory_nm", nullable = false, updatable = false, unique = true)
    private String observatoryName;

    @OneToMany(mappedBy = "observatory", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Region> regionList = new ArrayList<>();

    @Builder
    public Observatory(Long observatoryId, String observatoryName){
        this.observatoryId = observatoryId;
        this.observatoryName = observatoryName;
    }

}
