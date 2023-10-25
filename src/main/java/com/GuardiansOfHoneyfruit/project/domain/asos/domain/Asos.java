package com.GuardiansOfHoneyfruit.project.domain.asos.domain;

import com.GuardiansOfHoneyfruit.project.domain.observatory.domain.Observatory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Table(name = "asos")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Asos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long asosId;

    @ManyToOne
    @JoinColumn(name = "wt_observatory", nullable = false)
    private Observatory observatory;

    @Comment("관측 시각")
    @Column(name = "TM")
    private String time;

    @Comment("평균 기온")
    @Column(name = "TA_AVG")
    private Double avgTemperature;

    @Comment("최저 기온")
    @Column(name = "TA_MIN_TM")
    private Double minTemperature;

    @Comment("최고 기온")
    @Column(name = "TA_MAX_TM")
    private Double maxTemperature;

    @Comment("일 강수량")
    @Column(name = "RN_DAY")
    private Double rainDay;

    @Comment("최대 풍속")
    @Column(name = "WS_MAX")
    private Double maxWindSpeed;

    @Comment("평균 풍속")
    @Column(name = "WS_AVG")
    private Double avgWindSpeed;

    @Comment("최대 풍향")
    @Column(name = "WD_MAX")
    private Double windDirectionMax;

    @Comment("평균 상대습도")
    @Column(name = "HM_AVG")
    private Double avgHumidity;

    @Comment("합계 일사량")
    @Column(name = "SI_DAY")
    private Double solarRadiation;

    @Comment("평균 전운량")
    @Column(name = "CA_TOT")
    private Double avgTotalCloudAmount;

    @Comment("일 평균 지면 온도")
    @Column(name = "TS_AVG")
    private Double avgGroundTemperature;

    @Builder
    public Asos(Observatory observatory, String time, Double avgTemperature, Double minTemperature, Double maxTemperature, Double rainDay, Double maxWindSpeed, Double avgWindSpeed, Double windDirectionMax, Double avgHumidity, Double solarRadiation, Double avgTotalCloudAmount, Double avgGroundTemperature) {
        this.observatory = observatory;
        this.time = time;
        this.avgTemperature = avgTemperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.rainDay = rainDay;
        this.maxWindSpeed = maxWindSpeed;
        this.avgWindSpeed = avgWindSpeed;
        this.windDirectionMax = windDirectionMax;
        this.avgHumidity = avgHumidity;
        this.solarRadiation = solarRadiation;
        this.avgTotalCloudAmount = avgTotalCloudAmount;
        this.avgGroundTemperature = avgGroundTemperature;
    }

}
