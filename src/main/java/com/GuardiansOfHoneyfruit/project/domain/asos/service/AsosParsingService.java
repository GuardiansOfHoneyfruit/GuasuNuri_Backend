package com.GuardiansOfHoneyfruit.project.domain.asos.service;

import com.GuardiansOfHoneyfruit.project.domain.asos.dto.AsosEntityDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AsosParsingService {
    public List<AsosEntityDto> parse(String response, int startYear) {
        String[] lines = response.split("\n");
        List<AsosEntityDto> dtos = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith(Integer.toString(startYear))) {  // 데이터 라인인 경우만 처리합니다.
                String[] tokens = line.split("\\s+");
                AsosEntityDto dto = new AsosEntityDto(
                        tokens[0],              // time
                        Double.parseDouble(tokens[10]),  // avgTemperature
                        Double.parseDouble(tokens[13]),  // minTemperature
                        Double.parseDouble(tokens[11]),  // maxTemperature
                        Double.parseDouble(tokens[38]),  // rainDay
                        Double.parseDouble(tokens[5]),   // maxWindSpeed
                        Double.parseDouble(tokens[2]),   // avgWindSpeed
                        Double.parseDouble(tokens[4]),   // windDirectionMax
                        Double.parseDouble(tokens[18]),  // avgHumidity
                        Double.parseDouble(tokens[35]),  // solarRadiation
                        Double.parseDouble(tokens[31]),  // avgTotalCloudAmount
                        Double.parseDouble(tokens[16])   // avgGroundTemperature
                );

                dtos.add(dto);
            }
        }
        return dtos;
    }
    }
