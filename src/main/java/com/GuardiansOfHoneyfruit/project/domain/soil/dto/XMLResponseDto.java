package com.GuardiansOfHoneyfruit.project.domain.soil.dto;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@Getter
public class XMLResponseDto {
    @XmlElement(name = "header")
    private HeaderDto header;

    @XmlElement(name = "body")
    private BodyDto body;
}
