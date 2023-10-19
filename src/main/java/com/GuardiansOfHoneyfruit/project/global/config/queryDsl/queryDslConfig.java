package com.GuardiansOfHoneyfruit.project.global.config.queryDsl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class queryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory query(){
        return new JPAQueryFactory(entityManager);
    }
}
