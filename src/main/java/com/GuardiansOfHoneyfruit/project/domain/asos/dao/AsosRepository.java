package com.GuardiansOfHoneyfruit.project.domain.asos.dao;

import com.GuardiansOfHoneyfruit.project.domain.asos.domain.Asos;
import com.GuardiansOfHoneyfruit.project.domain.asos.dto.AsosEntityDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsosRepository extends JpaRepository<Asos, Long> {
    Asos findTopByObservatoryOrderByCreatedAtDesc(Long observatoryId);
}
