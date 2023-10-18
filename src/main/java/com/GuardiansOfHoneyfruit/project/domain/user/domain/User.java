package com.GuardiansOfHoneyfruit.project.domain.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "USER")
@Entity
public class User {

    @Id
    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "USER_UUID", nullable = false, updatable = false, unique = true)
    private String userUuid;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "ROLE", nullable = false)
    private String role;

    @Builder
    public User(Long userId, String userUuid, String email, String role) {
        this.userId = userId;
        this.userUuid = userUuid;
        this.email = email;
        this.role = role;
    }

}
