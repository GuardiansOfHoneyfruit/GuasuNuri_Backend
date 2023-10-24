package com.GuardiansOfHoneyfruit.project.domain.soil.dao;

import com.GuardiansOfHoneyfruit.project.domain.soil.domain.Soil;
import com.GuardiansOfHoneyfruit.project.domain.soil.exception.SoilNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SoilFindDao {

    private final SoilRepository soilRepository;

    public Soil findById(Long soilId){
        Soil soil = soilRepository.findById(soilId)
                .orElseThrow(() -> new SoilNotFoundException(soilId));
        return soil;
    }

}
