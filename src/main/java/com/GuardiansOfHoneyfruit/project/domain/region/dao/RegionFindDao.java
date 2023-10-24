package com.GuardiansOfHoneyfruit.project.domain.region.dao;

import com.GuardiansOfHoneyfruit.project.domain.region.domain.Region;
import com.GuardiansOfHoneyfruit.project.domain.region.exception.RegionNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionFindDao {

    private final RegionRepository regionRepository;

    public Region findById(String pnuCd){
        Region region = regionRepository.findById(pnuCd)
                .orElseThrow(() -> new RegionNotFoundException(pnuCd));
        return region;
    }

    public List<Region> findAll(){
        return regionRepository.findAll();
    }
}
