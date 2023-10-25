package com.GuardiansOfHoneyfruit.project.domain.observatory.dao;

import com.GuardiansOfHoneyfruit.project.domain.observatory.domain.Observatory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservatoryRepository extends JpaRepository<Observatory, Long> {
}
