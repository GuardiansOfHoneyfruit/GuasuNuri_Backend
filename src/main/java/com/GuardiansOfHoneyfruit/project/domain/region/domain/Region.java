package com.GuardiansOfHoneyfruit.project.domain.region.domain;

import com.GuardiansOfHoneyfruit.project.domain.observatory.domain.Observatory;
import com.GuardiansOfHoneyfruit.project.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "REGION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {

    @Id
    @Column(name = "REGION_CD")
    private String regionCode;

    @Column(name = "REGION_NM", nullable = false)
    private String regionName;

    @OneToMany(mappedBy = "region", orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Pnu> pnuData = new ArrayList<>();

    @OneToMany(mappedBy = "region", orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "observatory_cd", nullable = false)
    private Observatory observatory;

    @Builder
    public Region(Observatory observatory, String regionCode, String regionName){
        this.observatory = observatory;
        this.regionName = regionName;
        this.regionCode = regionCode;
    }

    public void addPnu(String pnuCode, String pnuAdress){
        this.pnuData.add(buildPnu(pnuCode, pnuAdress));
    }

    private Pnu buildPnu(String pnuCode, String pnuAddress){
        return Pnu.builder()
                .pnuAddress(pnuAddress)
                .region(this)
                .pnuCode(pnuCode)
                .build();
    }

}
