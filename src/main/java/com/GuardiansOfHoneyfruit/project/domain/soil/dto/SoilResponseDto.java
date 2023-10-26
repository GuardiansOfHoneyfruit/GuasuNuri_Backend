package com.GuardiansOfHoneyfruit.project.domain.soil.dto;

import com.GuardiansOfHoneyfruit.project.domain.region.domain.Pnu;
import com.GuardiansOfHoneyfruit.project.domain.soil.domain.Soil;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;

@Getter
public class SoilResponseDto {

    @XmlElement(name = "Any_Year")
    private Integer anyYear;

    @XmlElement(name = "Exam_Day")
    private String examDay;

    @XmlElement(name = "Exam_Type")
    private Integer examType;

    @XmlElement(name = "PNU_Nm")
    private String pnuNm;

    @XmlElement(name = "ACID")
    private String acidStr;

    @XmlElement(name = "VLDPHA")
    private Integer vldPha;

    @XmlElement(name = "VLDSIA")
    private Integer vldSia;

    @XmlElement(name = "OM")
    private Integer om;

    @XmlElement(name = "POSIFERT_MG")
    private String posifertMgStr;

    @XmlElement(name = "POSIFERT_K")
    private String posifertKStr;

    @XmlElement(name = "POSIFERT_CA")
    private Integer posifertCa;

    @XmlElement(name = "SELC")
    private String selcStr;

    public SoilResponseDto() {

    }

    public Soil toEntity(Pnu pnu) {
        return Soil.builder()
                .om(this.om)
                .pnuNm(this.pnuNm)
                .acid(parseToDouble(acidStr))
                .examDay(this.examDay)
                .posifertCa(this.posifertCa)
                .selc(parseToDouble(selcStr))
                .posifertK(parseToDouble(posifertKStr))
                .examType(this.examType)
                .vldSia(this.vldSia)
                .vldPha(this.vldPha)
                .anyYear(this.anyYear)
                .posifertMg(parseToDouble(posifertMgStr))
                .pnu(pnu)
                .build();
    }

    private Double parseToDouble(String value) {
        if (value != null && !value.isEmpty()) {
            return Double.parseDouble(value);
        }
        return null;
    }
}
