package com.GuardiansOfHoneyfruit.project.domain.soil.dto;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;

@Getter
public class HeaderDto {
    @XmlElement(name = "Result_Code")
    private String resultCode;

    @XmlElement(name = "Result_Msg")
    private String resultMsg;
}
