package com.GuardiansOfHoneyfruit.project.domain.asos.dao;

import com.GuardiansOfHoneyfruit.project.domain.asos.domain.Asos;
import com.GuardiansOfHoneyfruit.project.domain.asos.dto.AsosEntityDto;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AsosRepository extends JpaRepository<Asos, Long> {
    @Query("SELECT a FROM Asos a WHERE a.observatory.observatoryId = :observatoryId ORDER BY a.createdAt DESC LIMIT 1")
    Asos findTopByObservatoryIdOrderByCreatedAtDesc(@Param("observatoryId") Long observatoryId);
}
