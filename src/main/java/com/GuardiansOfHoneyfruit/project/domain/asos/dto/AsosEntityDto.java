package com.GuardiansOfHoneyfruit.project.domain.asos.dto;

import com.GuardiansOfHoneyfruit.project.domain.observatory.exception.InvalidTokenLengthException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AsosEntityDto {
    @Valid private String time;
    @Valid private Double avgTemperature;
    @Valid private Double minTemperature;
    @Valid private Double maxTemperature;
    @Valid private Double rainDay;
    @Valid private Double maxWindSpeed;
    @Valid private Double avgWindSpeed;
    @Valid private Double windDirectionMax;
    @Valid private Double avgHumidity;
    @Valid private Double solarRadiation;
    @Valid private Double avgTotalCloudAmount;
    @Valid private Double avgGroundTemperature;

    public static AsosEntityDto fromTokens(String[] tokens, int standardTokenLength) {
        System.out.println(tokens.length + "개다");
        if (tokens.length != standardTokenLength) {
            throw new InvalidTokenLengthException(standardTokenLength, tokens.length);
        }

        return new AsosEntityDto(
                tokens[0],
                parseToken(tokens[10]),
                parseToken(tokens[13]),
                parseToken(tokens[11]),
                parseToken(tokens[38]),
                parseToken(tokens[5]),
                parseToken(tokens[2]),
                parseToken(tokens[4]),
                parseToken(tokens[18]),
                parseToken(tokens[35]),
                parseToken(tokens[31]),
                parseToken(tokens[16])
        );
    }

    private static double parseToken(String token) {
        if ("-9.0".equals(token) || "-99.0".equals(token) || "-9".equals(token)) {
            return 0;
        }
        return Double.parseDouble(token);
    }
}
