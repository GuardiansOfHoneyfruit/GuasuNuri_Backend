package com.GuardiansOfHoneyfruit.project.domain.asos.dao;

import com.GuardiansOfHoneyfruit.project.domain.observatory.domain.Observatory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsosRepository extends JpaRepository<Observatory, Long> {
}
