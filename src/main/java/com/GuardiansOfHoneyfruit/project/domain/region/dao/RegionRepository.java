package com.GuardiansOfHoneyfruit.project.domain.region.dao;

import com.GuardiansOfHoneyfruit.project.domain.region.domain.Region;
import com.GuardiansOfHoneyfruit.project.domain.region.dto.RegionNameDto;
import com.GuardiansOfHoneyfruit.project.domain.region.dto.RegionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, String> {

    @Query("SELECT new com.GuardiansOfHoneyfruit.project.domain.region.dto.RegionRecord(r.regionName, r.regionCode) FROM Region r")
    List<RegionRecord> findAllRegionNameAndRegionCode();

    @Query("SELECT new com.GuardiansOfHoneyfruit.project.domain.region.dto.RegionNameDto(r.regionName, r.regionCode) FROM Region r")
    List<RegionNameDto> findAllRegionDto();

    @Query("SELECT r.regionCode FROM Region r")
    List<String> findAllRegionCode();
}

