package com.GuardiansOfHoneyfruit.project.domain.region.dao;

import com.GuardiansOfHoneyfruit.project.domain.region.code.RegionCode;
import com.GuardiansOfHoneyfruit.project.domain.region.domain.Region;
import com.GuardiansOfHoneyfruit.project.domain.region.dto.RegionNameDto;
import com.GuardiansOfHoneyfruit.project.global.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionFindDao {

    private final RegionRepository regionRepository;

    public Response findAllRegionName(){
        List<RegionNameDto> regionResponse = regionRepository.findAllRegionDto();
        return Response.of(RegionCode.GET_ALL_REGION_NAME, regionResponse);
    }

    public List<String> findAllRegionCode(){
        return regionRepository.findAllRegionCode();
    }

    public Region findRegionReference(String regionCode){
        return regionRepository.getReferenceById(regionCode);
    }

}
