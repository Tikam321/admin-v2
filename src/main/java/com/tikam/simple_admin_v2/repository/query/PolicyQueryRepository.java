package com.tikam.simple_admin_v2.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikam.simple_admin_v2.entity.policy.QCompanyLicensePolicy;
import com.tikam.simple_admin_v2.entity.policy.QCompanyLicensePolicyRule;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.tikam.simple_admin_v2.entity.policy.QCompanyLicensePolicy.companyLicensePolicy;
import static com.tikam.simple_admin_v2.entity.policy.QCompanyLicensePolicyRule.companyLicensePolicyRule;
import static com.tikam.simple_admin_v2.entity.policy.QOrgPolicyRule.orgPolicyRule;
import static com.tikam.simple_admin_v2.entity.policy.QPolicyRule.policyRule;

@Service
@RequiredArgsConstructor
public class PolicyQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    public Map<Integer, String> getDefaultPolicyMap(List<Integer> policyIds) {
        Map<Integer, String> transform = jpaQueryFactory.from(companyLicensePolicy)
                .innerJoin(companyLicensePolicyRule)
                .on(companyLicensePolicy.companylcsPolicyId
                        .eq(companyLicensePolicyRule.companyLcsPolicyId))
                .where(companyLicensePolicy.companyId.eq(1),
                        companyLicensePolicy.lcsId.eq(1),
                        companyLicensePolicy.useYn.eq(1),
                        companyLicensePolicyRule.policyRuleId.in(policyIds))
                .transform(groupBy(companyLicensePolicyRule.policyRuleId)
                        .as(companyLicensePolicyRule.controlValue));
        System.out.println(transform);
        return transform;
    }

    public Map<Integer, String> getCompanyPolicy(String companyCode,List<Integer> policyIds) {
        return getSubOrgPolicy(companyCode, "ALL",policyIds);
    }

    public Map<Integer, String> getSubOrgPolicy(String companyCode , String subOrgCode,List<Integer> policuyIds) {
        return jpaQueryFactory.from(orgPolicyRule)
                .where(orgPolicyRule.subOrgCode.eq(subOrgCode),
                        orgPolicyRule.companyCode.eq(companyCode),
                        orgPolicyRule.policyRuleId.in(policuyIds))
                .transform(groupBy(orgPolicyRule.policyRuleId)
                        .as(orgPolicyRule.controlValue));
    }
}
