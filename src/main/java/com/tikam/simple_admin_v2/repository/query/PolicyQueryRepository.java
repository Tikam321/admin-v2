package com.tikam.simple_admin_v2.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikam.simple_admin_v2.dto.policy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.tikam.simple_admin_v2.entity.QCSQ_TBT.cSQ_TBT;
import static com.tikam.simple_admin_v2.entity.QUser.user;
import static com.tikam.simple_admin_v2.entity.policy.QCompanyLicensePolicy.companyLicensePolicy;
import static com.tikam.simple_admin_v2.entity.policy.QCompanyLicensePolicyRule.companyLicensePolicyRule;
import static com.tikam.simple_admin_v2.entity.policy.QOrgPolicyRule.orgPolicyRule;
import static com.tikam.simple_admin_v2.entity.policy.QPolicyRule.policyRule;
import static com.tikam.simple_admin_v2.entity.policy.QUserPolicyRule.userPolicyRule;

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

    public Optional<List<CompanyPolicyResponse>> getCompanyList(int companyId) {
        List<CompanyPolicyResponse> companyPolicyList = jpaQueryFactory.select(new
                        QCompanyPolicyResponse(
                        companyLicensePolicy.companylcsPolicyId,
                        companyLicensePolicyRule.policyRuleId,
                        companyLicensePolicy.policyTypeCode,
                        companyLicensePolicy.policyName,
                        companyLicensePolicy.policyDescription,
                        policyRule.englishRuleText,
                        companyLicensePolicyRule.controlValue
                )).from(companyLicensePolicy)
                .innerJoin(companyLicensePolicyRule)
                .on(companyLicensePolicy.companylcsPolicyId.eq(companyLicensePolicyRule.companyLcsPolicyId))
                .innerJoin(policyRule)
                .on(policyRule.policyRuleId.eq(companyLicensePolicyRule.policyRuleId))
                .where(companyLicensePolicy.companyId.eq(companyId))
                .fetch();
        return Optional.ofNullable(companyPolicyList);
    }

    public Optional<CompanyPolicyResponse> getCompanyPolicy(int companyId, int policyId) {
        List<CompanyPolicyResponse> companyPolicyList = jpaQueryFactory.select(new
                        QCompanyPolicyResponse(
                        companyLicensePolicy.companylcsPolicyId,
                        companyLicensePolicyRule.policyRuleId,
                        companyLicensePolicy.policyTypeCode,
                        companyLicensePolicy.policyName,
                        companyLicensePolicy.policyDescription,
                        policyRule.englishRuleText,
                        companyLicensePolicyRule.controlValue
                )).from(companyLicensePolicy)
                .innerJoin(companyLicensePolicyRule)
                .on(companyLicensePolicy.companylcsPolicyId.eq(companyLicensePolicyRule.companyLcsPolicyId))
                .innerJoin(policyRule)
                .on(policyRule.policyRuleId.eq(companyLicensePolicyRule.policyRuleId))
                .where(companyLicensePolicy.companyId.eq(companyId).and(companyLicensePolicy.companylcsPolicyId.eq(policyId)))
                .fetch();
        System.out.println("in company policy");
        System.out.println(companyPolicyList);
        return Optional.ofNullable(companyPolicyList.get(0));
    }

    public Optional<String> getDefaultPolicyValue(int policyId, int companyLicensePolicyId) {
         String defaultValue = jpaQueryFactory
                 .select(companyLicensePolicyRule.controlValue)
                 .from(companyLicensePolicyRule)
                 .where(companyLicensePolicyRule.policyRuleId.eq(policyId)
                         .and(companyLicensePolicyRule.companyLcsPolicyId
                                 .eq(companyLicensePolicyId)))
                 .fetchFirst();
        return Optional.ofNullable(defaultValue);
    }

    public Optional<PolicyResponse> getPolicyRuleValueCheck(int policyId) {
        PolicyResponse policyResponse = jpaQueryFactory
                .select(new QPolicyResponse(
                        policyRule.dataType,
                        policyRule.userPolicyRuleYn,
                        policyRule.securityGradePolicyRuleYn,
                        policyRule.ruleTypeValue,
                        policyRule.ruleText,
                        policyRule.policyUnit,
                        policyRule.policyRuleId,
                        policyRule.policyGroupId,
                        policyRule.policyAlignNo,
                        policyRule.permanentControlValue,
                        policyRule.orgPolicyRuleYn,
                        policyRule.englishRuleText,
                        policyRule.deliverTenantAdminYn,
                        policyRule.deliverClientYn
                ))
                .from(policyRule)
                .where(policyRule.policyRuleId.eq(policyId)).fetchFirst();
        return Optional.ofNullable(policyResponse);
    }

    public Optional<List<StoredPolicyValue>> getStoredCompanyPolicyRules(String companyCode, Long userId) {
        List<StoredPolicyValue> storesPolicyList = jpaQueryFactory.select(new QStoredPolicyValue(orgPolicyRule.policyRuleId, orgPolicyRule.controlValue))
                .from(orgPolicyRule)
                .innerJoin(cSQ_TBT)
                .on(cSQ_TBT.companyCode.eq(orgPolicyRule.companyCode).and(cSQ_TBT.subOrgCode
                        .eq("_ALL_")))
                .innerJoin(user)
                .on(user.userEpId.eq(cSQ_TBT.employeeId))
                .innerJoin(policyRule)
                .on(policyRule.policyRuleId.eq(orgPolicyRule.policyRuleId))
                .where(user.userId.eq(userId))
                .fetch();
        return Optional.ofNullable(storesPolicyList);
    }

    public Optional<List<StoredPolicyValue>> getStoredSubOrgPolicyRules(String companyCode,String subOrgCode, Long userId) {
        List<StoredPolicyValue> storesPolicyList = jpaQueryFactory.select(new QStoredPolicyValue(orgPolicyRule.policyRuleId, orgPolicyRule.controlValue))
                .from(orgPolicyRule)
                .innerJoin(cSQ_TBT)
                .on(cSQ_TBT.companyCode.eq(orgPolicyRule.companyCode).and(cSQ_TBT.subOrgCode.eq(subOrgCode)))
                .innerJoin(user)
                .on(user.userEpId.eq(cSQ_TBT.employeeId))
                .innerJoin(policyRule)
                .on(policyRule.policyRuleId.eq(orgPolicyRule.policyRuleId))
                .where(user.userId.eq(userId).and(cSQ_TBT.companyCode.eq(companyCode)).and(cSQ_TBT.subOrgCode.eq(subOrgCode)))
                .fetch();
        return Optional.ofNullable(storesPolicyList);
    }

    public Optional<List<StoredPolicyValue>> getStoredUserPolicyRules( Long userId) {
        List<StoredPolicyValue> storesPolicyList = jpaQueryFactory.select(new QStoredPolicyValue(userPolicyRule.policyRuleId, userPolicyRule.controlValue))
                .from(userPolicyRule)
                .innerJoin(user)
                .on(user.userId.eq(userPolicyRule.userId))
                .where(user.userId.eq(userId))
                .fetch();
        return Optional.ofNullable(storesPolicyList);
    }
}
