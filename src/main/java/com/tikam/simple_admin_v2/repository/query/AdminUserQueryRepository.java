package com.tikam.simple_admin_v2.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikam.simple_admin_v2.entity.admin.AdminUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminUserQueryRepository {
    private  final JPAQueryFactory jpaQueryFactory;

//    public AdminUser getJpaQueryFactory(long userId) {
//        return jpaQueryFactory.select
//    }
}
