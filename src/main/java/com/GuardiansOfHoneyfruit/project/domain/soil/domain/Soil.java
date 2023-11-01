package com.GuardiansOfHoneyfruit.project.domain.soil.domain;

import com.GuardiansOfHoneyfruit.project.domain.region.domain.Pnu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "SOIL")
public class Soil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOIL_DATA_ID", columnDefinition = "BIGINT COMMENT '데이터의 고유 ID'")
    private Long soilDataId;

    @ManyToOne
    @JoinColumn(name = "PNU_CD", nullable = false)
    private Pnu pnu;

    @Column(name = "ANY_YEAR", columnDefinition = "INT COMMENT '연도'")
    private Integer anyYear;

    @Column(name = "EXAM_DAY", columnDefinition = "VARCHAR(255) COMMENT '검사 날짜'")
    private String examDay;

    @Column(name = "EXAM_TYPE", columnDefinition = "INT COMMENT '검사 유형'")
    private Integer examType;

    @Column(name = "PNU_NM", columnDefinition = "TEXT COMMENT 'PNU 이름'")
    private String pnuNm;

    @Column(name = "ACID", columnDefinition = "DOUBLE COMMENT '산도'")
    private Double acid;

    @Column(name = "VLD_PHA", columnDefinition = "INT COMMENT 'VLDPHA 값'")
    private Integer vldPha;

    @Column(name = "VLD_SIA", columnDefinition = "INT COMMENT 'VLDSIA 값'")
    private Integer vldSia;

    @Column(name = "OM", columnDefinition = "INT COMMENT 'OM 값'")
    private Integer om;

    @Column(name = "POSIFERT_MG", columnDefinition = "DOUBLE COMMENT 'POSIFERT MG 값'")
    private Double posifertMg;

    @Column(name = "POSIFERT_K", columnDefinition = "DOUBLE COMMENT 'POSIFERT K 값'")
    private Double posifertK;

    @Column(name = "POSIFERT_CA", columnDefinition = "INT COMMENT 'POSIFERT CA 값'")
    private Integer posifertCa;

    @Column(name = "SELC", columnDefinition = "DOUBLE COMMENT 'SELC 값'")
    private Double selc;

    @Builder
    public Soil(Pnu pnu, Integer anyYear, String examDay, Integer examType, String pnuNm, Double acid,
                Integer vldPha, Integer vldSia, Integer om, Double posifertMg, Double posifertK,
                Integer posifertCa, Double selc){
        this.pnu = pnu;
        this.anyYear = anyYear;
        this.examDay = examDay;
        this.examType = examType;
        this.pnuNm = pnuNm;
        this.acid = acid;
        this.vldPha = vldPha;
        this.vldSia = vldSia;
        this.om = om;
        this.posifertMg = posifertMg;
        this.posifertK = posifertK;
        this.posifertCa = posifertCa;
        this.selc = selc;
    }

}
