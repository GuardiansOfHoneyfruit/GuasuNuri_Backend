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

        // 쿼리 결과가 존재하는지 여부를 확인하는 쿼리
        Integer fetchResult = jpaQueryFactory
                .selectOne()
                .from(qUser)
                .where(qUser.userUuid.eq(userUuid)
                        .and(qUser.region.isNull()))
                .fetchFirst(); // fetchOne() 대신 fetchFirst()를 사용하여 존재 여부 확인

        // 쿼리 결과가 null이 아니면 true를 반환 (존재한다는 의미)
        return fetchResult != null;
    }
}
