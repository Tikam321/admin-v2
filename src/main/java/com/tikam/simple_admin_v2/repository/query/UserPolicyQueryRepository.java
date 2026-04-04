package com.tikam.simple_admin_v2.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikam.simple_admin_v2.dto.policy.userPolicyDto.PolicyUserResponse;
import com.tikam.simple_admin_v2.dto.policy.userPolicyDto.QPolicyUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.tikam.simple_admin_v2.entity.QCSQ_TBT.cSQ_TBT;
import static com.tikam.simple_admin_v2.entity.QUser.user;
import static com.tikam.simple_admin_v2.entity.policy.QUserPolicyRule.userPolicyRule;

@RequiredArgsConstructor
@Repository
public class UserPolicyQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<List<PolicyUserResponse>> getUserList(String companyCode, String subOrgCode) {
        List<PolicyUserResponse> userList = jpaQueryFactory.select(
                        new QPolicyUserResponse(
                                user.userId,
                                user.createdUnixTime,
                                user.updatedUnixTime,
                                cSQ_TBT.companyName,
                                user.localName,
                                user.departmentCode,
                                user.userEpId
                        )
                )
                .from(user)
                .innerJoin(cSQ_TBT)
                .on(user.userEpId.eq(cSQ_TBT.employeeId))
                .innerJoin(userPolicyRule)
                .on(userPolicyRule.userId.eq(user.userId))
                .where(cSQ_TBT.companyCode.eq(companyCode).and(cSQ_TBT.subOrgCode.eq(subOrgCode)))
                .fetch();
        return Optional.ofNullable(userList);

    }

    public Optional<List<PolicyUserResponse>> getSearchUserByPolicy(String companyCode, String subOrgCode, int policyRulId) {
        List<PolicyUserResponse> userList = jpaQueryFactory.select(
                        new QPolicyUserResponse(
                                user.userId,
                                user.createdUnixTime,
                                user.updatedUnixTime,
                                cSQ_TBT.companyName,
                                user.localName,
                                user.departmentCode,
                                user.userEpId
                        )
                )
                .from(user)
                .innerJoin(cSQ_TBT)
                .on(user.userEpId.eq(cSQ_TBT.employeeId))
                .innerJoin(userPolicyRule)
                .on(userPolicyRule.userId.eq(user.userId))
                .where(cSQ_TBT.companyCode.eq(companyCode).and(cSQ_TBT.subOrgCode.eq(subOrgCode)
                                .and(userPolicyRule.policyRuleId.eq(policyRulId))))
                .fetch();
        return Optional.ofNullable(userList);

    }



}
