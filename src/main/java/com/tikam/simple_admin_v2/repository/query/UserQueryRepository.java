package com.tikam.simple_admin_v2.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikam.simple_admin_v2.dto.user.LoginInfoDto;
import com.tikam.simple_admin_v2.dto.user.QLoginInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.tikam.simple_admin_v2.entity.QCSQ_TBT.cSQ_TBT;
import static com.tikam.simple_admin_v2.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<Long> getLastUserCount(String companyCode, String suborgCode) {
        return Optional.ofNullable(jpaQueryFactory.select(user.count())
                .from(user)
                .innerJoin(cSQ_TBT).on(user.userEpId.eq(cSQ_TBT.employeeId))
                .where(cSQ_TBT.subOrgCode.eq(suborgCode).and(cSQ_TBT.companyCode.eq(companyCode)))
                .fetchOne());
    }

    public Map<String, LoginInfoDto> getLastLoginInfo(String companyCode, String suborgCode,int offset, int limit) {

        QLoginInfoDto loginInfoDto =
                new QLoginInfoDto(
                        user.userEpId,
                        user.createdUnixTime,
                        user.localName,
                        user.departmentCode,
                        cSQ_TBT.subOrgCode,
                        cSQ_TBT.companyName
                );

        return jpaQueryFactory.select(user)
                .from(user)
                .innerJoin(cSQ_TBT)
                .on(user.userEpId.eq(cSQ_TBT.employeeId))
                .where(cSQ_TBT.subOrgCode.eq(suborgCode).and(cSQ_TBT.companyCode.eq(companyCode)))
                .offset(offset)
                .limit(limit)
                .orderBy(cSQ_TBT.employeeId.desc())
                .transform(groupBy(cSQ_TBT.employeeId).as(loginInfoDto));
    }
}
