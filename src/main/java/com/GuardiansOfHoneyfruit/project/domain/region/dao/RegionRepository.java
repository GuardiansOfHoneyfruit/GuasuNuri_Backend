package com.GuardiansOfHoneyfruit.project.domain.region.dao;

import com.GuardiansOfHoneyfruit.project.domain.region.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, String> {
}
