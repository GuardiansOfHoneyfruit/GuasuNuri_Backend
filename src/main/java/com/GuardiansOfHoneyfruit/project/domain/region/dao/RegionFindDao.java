package com.GuardiansOfHoneyfruit.project.domain.region.dao;

import com.GuardiansOfHoneyfruit.project.domain.region.domain.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionFindDao {

    private final RegionRepository regionRepository;

    public Region findRegionReference(String regionCode){
        return regionRepository.getReferenceById(regionCode);
    }

}
