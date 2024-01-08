package com.GuardiansOfHoneyfruit.project.domain.asos.dao;

import com.GuardiansOfHoneyfruit.project.domain.asos.domain.Asos;
import com.GuardiansOfHoneyfruit.project.domain.asos.dto.AsosEntityDto;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AsosRepository extends JpaRepository<Asos, Long> {
    @Query(value = "SELECT * FROM asos WHERE wt_observatory = :observatoryId ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Asos findTopByObservatoryIdOrderByCreatedAtDescNative(@Param("observatoryId") Long observatoryId);
}
