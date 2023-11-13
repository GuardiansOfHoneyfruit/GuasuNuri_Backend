package com.GuardiansOfHoneyfruit.project.domain.region.service;

import com.GuardiansOfHoneyfruit.project.domain.region.code.RegionCode;
import com.GuardiansOfHoneyfruit.project.domain.region.dao.RegionFindDao;
import com.GuardiansOfHoneyfruit.project.domain.region.domain.Danger;
import com.GuardiansOfHoneyfruit.project.domain.region.dto.DangerResponse;
import com.GuardiansOfHoneyfruit.project.domain.region.dto.RiskConversionResponse;
import com.GuardiansOfHoneyfruit.project.global.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionDangerService {

    private final RedisTemplate<String, DangerResponse> redisTemplate;
    private final RegionFindDao regionFindDao;

    public void updateDangerLevelToRedis(final RiskConversionResponse riskConversionResponse){
        String key = keyGenerator(riskConversionResponse.getRegionCode());
        DangerResponse response = DangerResponse.from(Danger.getDanger(riskConversionResponse.getRiskOfDegree()), riskConversionResponse);
        redisTemplate.opsForValue().set(key, response);
    }

    public Response getDangerLevelAtSingleRegion(final String regionCode){
        String key = keyGenerator(regionCode);
        DangerResponse response = redisTemplate.opsForValue().get(key);
        return Response.of(RegionCode.GET_DEGREE_OF_RISK_SINGLE_REGION, response);
    }

    public Response getAllDangerLevel(){
        List<String> regionCodes = regionFindDao.findAllRegionCode();
        List<DangerResponse> responses = regionCodes.stream()
                .map(this::keyGenerator)
                .map(key -> redisTemplate.opsForValue().get(key))
                .collect(Collectors.toList());
        return Response.of(RegionCode.GET_ALL_DEGREE_OF_RISK, responses);
    }

    private String keyGenerator(String regionCode){
        return "risk_" + regionCode;
    }

}
