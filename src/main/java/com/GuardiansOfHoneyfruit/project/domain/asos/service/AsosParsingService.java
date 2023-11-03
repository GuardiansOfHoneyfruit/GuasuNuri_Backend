package com.GuardiansOfHoneyfruit.project.domain.asos.service;

import com.GuardiansOfHoneyfruit.project.domain.asos.dto.AsosEntityDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AsosParsingService {

    private static final int STANDARD_TOKEN_LENGTH = 56;

    public List<AsosEntityDto> parse(String response, int startYear) {
        return Arrays.stream(response.split("\n")) // 스트림 생성
                .filter(line -> line.startsWith(Integer.toString(startYear)))
                .map(line -> line.split(",")) // 쉼표로 분리
                .map(tokens -> AsosEntityDto.fromTokens(tokens, STANDARD_TOKEN_LENGTH))
                .collect(Collectors.toList());
    }
}
