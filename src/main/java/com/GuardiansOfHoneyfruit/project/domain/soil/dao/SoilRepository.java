package com.GuardiansOfHoneyfruit.project.domain.soil.dao;

import com.GuardiansOfHoneyfruit.project.domain.soil.domain.Soil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoilRepository extends JpaRepository<Soil, Long> {
}
