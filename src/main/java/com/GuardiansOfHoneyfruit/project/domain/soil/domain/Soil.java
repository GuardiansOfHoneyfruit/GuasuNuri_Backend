package com.GuardiansOfHoneyfruit.project.domain.soil.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "soil")
public class Soil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soil_data_id", columnDefinition = "BIGINT COMMENT '데이터의 고유 ID'")
    private Long soilDataId;

    @Column(name = "bjd_code", columnDefinition = "TEXT COMMENT 'BJD 코드'")
    private String bjdCode;

    @Column(name = "any_year", columnDefinition = "INT COMMENT '연도'")
    private Integer anyYear;

    @Column(name = "exam_day", columnDefinition = "VARCHAR(255) COMMENT '검사 날짜'")
    private String examDay;

    @Column(name = "exam_type", columnDefinition = "INT COMMENT '검사 유형'")
    private Integer examType;

    @Column(name = "pnu_nm", columnDefinition = "TEXT COMMENT 'PNU 이름'")
    private String pnuNm;

    @Column(name = "acid", columnDefinition = "DOUBLE COMMENT '산도'")
    private Double acid;

    @Column(name = "vld_pha", columnDefinition = "INT COMMENT 'VLDPHA 값'")
    private Integer vldPha;

    @Column(name = "vld_sia", columnDefinition = "INT COMMENT 'VLDSIA 값'")
    private Integer vldSia;

    @Column(name = "om", columnDefinition = "INT COMMENT 'OM 값'")
    private Integer om;

    @Column(name = "posifert_mg", columnDefinition = "DOUBLE COMMENT 'POSIFERT MG 값'")
    private Double posifertMg;

    @Column(name = "posifert_k", columnDefinition = "DOUBLE COMMENT 'POSIFERT K 값'")
    private Double posifertK;

    @Column(name = "posifert_ca", columnDefinition = "INT COMMENT 'POSIFERT CA 값'")
    private Integer posifertCa;

    @Column(name = "selc", columnDefinition = "DOUBLE COMMENT 'SELC 값'")
    private Double selc;

    @Builder
    public Soil(String bjdCode, Integer anyYear, String examDay, Integer examType, String pnuNm, Double acid,
                Integer vldPha, Integer vldSia, Integer om, Double posifertMg, Double posifertK,
                Integer posifertCa, Double selc){
        this.bjdCode = bjdCode;
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
