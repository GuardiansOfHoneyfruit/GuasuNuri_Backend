package com.GuardiansOfHoneyfruit.project.domain.user.dao;

import com.GuardiansOfHoneyfruit.project.domain.user.domain.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public boolean isUserRegionNull(String userUuid){
        QUser qUser = QUser.user;

        Integer fetchResult = jpaQueryFactory
                .selectOne()
                .from(qUser)
                .where(qUser.userUuid.eq(userUuid)
                        .and(qUser.region.isNull()))
                .fetchFirst();

        return fetchResult == null;
    }
}
