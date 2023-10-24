package com.GuardiansOfHoneyfruit.project.domain.asos.domain;

import com.GuardiansOfHoneyfruit.project.domain.observatory.domain.Observatory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "asos")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Asos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long asosId;

    @ManyToOne
    @JoinColumn(name = "wt_observatory", nullable = false)
    private Observatory observatory;
}
