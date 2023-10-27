package com.GuardiansOfHoneyfruit.project.domain.asos.service;

import com.GuardiansOfHoneyfruit.project.domain.asos.dto.AsosEntityDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AsosParsingService {

    private final int STANDARD_TOKEN_LENGTH = 56;

    public List<AsosEntityDto> parse(String response, int startYear) {
        String[] lines = response.split("\n");
        List<AsosEntityDto> dtos = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith(Integer.toString(startYear))) {
                String[] tokens = line.split(",");
                dtos.add(AsosEntityDto.fromTokens(tokens, STANDARD_TOKEN_LENGTH));
            }
        }
        return dtos;
    }
    }
