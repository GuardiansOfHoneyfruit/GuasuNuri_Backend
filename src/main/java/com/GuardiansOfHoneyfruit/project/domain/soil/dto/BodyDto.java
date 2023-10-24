package com.GuardiansOfHoneyfruit.project.domain.soil.dto;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@Getter
public class BodyDto {
    @XmlElement(name = "Rcdcnt")
    private Integer recordCount;

    @XmlElement(name = "Page_No")
    private Integer pageNo;

    @XmlElement(name = "Total_Count")
    private Integer totalCount;

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<SoilResponseDto> items;}
