package com.GuardiansOfHoneyfruit.project.domain.region.dao;

import com.GuardiansOfHoneyfruit.project.domain.region.domain.Region;
import com.GuardiansOfHoneyfruit.project.domain.region.dto.RegionNameDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, String> {

    @Query("SELECT new com.GuardiansOfHoneyfruit.project.domain.region.dto.RegionNameDto(r.regionName, r.regionCode) FROM Region r")
    List<RegionNameDto> findAllRegionDto();
}

