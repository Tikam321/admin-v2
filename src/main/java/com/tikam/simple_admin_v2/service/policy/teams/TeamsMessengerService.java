package com.tikam.simple_admin_v2.service.policy.teams;

import com.tikam.simple_admin_v2.annotation.validation.Teams;
import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.policy.*;
import com.tikam.simple_admin_v2.entity.policy.CompanyLicensePolicyRule;
import com.tikam.simple_admin_v2.entity.policy.PolicyRule;
import com.tikam.simple_admin_v2.exception.AdminException;
import com.tikam.simple_admin_v2.exception.ErrorCode;
import com.tikam.simple_admin_v2.repository.policy.CompanyLicensePolicyRuleRepository;
import com.tikam.simple_admin_v2.repository.policy.PolicyRuleRepository;
import com.tikam.simple_admin_v2.repository.query.PolicyQueryRepository;
import com.tikam.simple_admin_v2.service.policy.PolicyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//@Teams
@Slf4j
public class TeamsMessengerService extends PolicyService {
    private final CompanyLicensePolicyRuleRepository companyLicensePolicyRuleRepository;
    private final PolicyRuleRepository policyRuleRepository;
    protected TeamsMessengerService(PolicyQueryRepository policyQueryRepository, CompanyLicensePolicyRuleRepository companyLicensePolicyRuleRepository, PolicyRuleRepository policyRuleRepository) {
        super(policyQueryRepository);
        this.companyLicensePolicyRuleRepository = companyLicensePolicyRuleRepository;
        this.policyRuleRepository = policyRuleRepository;
    }

    @Override
    public APIResponse<PortalUserPolicyResponse> getUserPolicyForPortal(PortalPolicyRequest portalPolicyRequest) {
        throw new AdminException(ErrorCode.NOT_FOUND, "the user policy for portal is not found in teams");
    }
    public List<CompanyPolicyResponse> getCompanyList(int companyId) {
        List<CompanyPolicyResponse> companyPolicyList = policyQueryRepository.getCompanyList(companyId)
                .orElseThrow(() ->new AdminException(ErrorCode.NOT_FOUND, "Company policy not found"));
        System.out.println(companyPolicyList);
        return companyPolicyList;
    }

    @Override
    public CompanyPolicyResponse getCompanyPolicy(int companyId, int policyId) {
        CompanyPolicyResponse companyPolicyList = policyQueryRepository.getCompanyPolicy(companyId,policyId)
                .orElseThrow(() ->new AdminException(ErrorCode.NOT_FOUND, "Company policy not found"));
        System.out.println(companyPolicyList);
        return companyPolicyList;
    }

    @Override
    public PolicyResponse getDefaultPolicy(int policyLicenseId, int policyId) {
        PolicyResponse policyRuleValue = policyQueryRepository.getPolicyRuleValueCheck(policyId)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "Company policy not found"));

        String defaultControlValue = policyQueryRepository.getDefaultPolicyValue(policyId, policyLicenseId)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "Default policy value not found"));

        policyRuleValue.setPermanentControlValue(defaultControlValue);

        return policyRuleValue;
    }

    @Transactional
    @Override
    public APIResponse addPolicy(AddPolicyRequest policyRequest) {
        PolicyRule policyRule = new PolicyRule(policyRequest.getPolicyRuleId(), policyRequest.getRuleText(), policyRequest.getRuleText(),
                policyRequest.getRuleTypeValue(), policyRequest.getDeliverClientYn(), policyRequest.getDeliverTenantAdminYn(),policyRequest.getPolicyGroupId(),
                policyRequest.getDataType(), policyRequest.getPolicyUnit(), policyRequest.getPermanentControlValue(), policyRequest.getOrgPolicyRuleYn(),
                policyRequest.getSecurityGradePolicyRuleYn(), policyRequest.getUserPolicyRuleYn(), policyRequest.getPolicyAlignNo());

        CompanyLicensePolicyRule companyLicensePolicyRule = new CompanyLicensePolicyRule(101,policyRequest.getPolicyRuleId(), policyRequest.getPermanentControlValue());
        companyLicensePolicyRuleRepository.save(companyLicensePolicyRule);
        log.info("policy is added in companyLicensePolicy table");
        policyRuleRepository.save(policyRule);
        log.info("policy is added in policyRule table");
        return APIResponse.added();
    }
}
