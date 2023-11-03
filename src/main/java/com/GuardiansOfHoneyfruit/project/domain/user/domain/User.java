package com.GuardiansOfHoneyfruit.project.domain.user.domain;

import com.GuardiansOfHoneyfruit.project.domain.region.domain.Region;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "USER")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "USER_UUID", nullable = false, updatable = false, unique = true)
    private String userUuid;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "ROLE", nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "REGION_CD", nullable = true, updatable = true)
    private Region region;

    @Builder
    public User(String userUuid, String email, String role) {
        this.userUuid = userUuid;
        this.email = email;
        this.role = role;
    }

    public void updateRegion(Region region){
        this.region = region;
    }

}
