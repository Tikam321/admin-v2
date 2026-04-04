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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return APIResponse.added(true);
    }
    public StoredPolicyRuleResponse allUserPolicyGet(Long userId, String companyCode,String subOrgCode) {
        List<CompanyPolicyResponse> companyList = policyQueryRepository.getCompanyList(1)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "Company policy not found"));
        log.info("the default company policy list is fetched");
        List<StoredPolicyValue> companyStoredPolicies = policyQueryRepository.getStoredCompanyPolicyRules(companyCode, userId)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "Company policy not found"));
        log.info("the company policy list is fetched");

        Map<Integer, String> defaultPolicyValueMap = new HashMap<>();

        Map<Integer, String> companyStoredPolicyMap = new HashMap<>();
        for(StoredPolicyValue storedPolicyValue: companyStoredPolicies) {
            companyStoredPolicyMap.put(storedPolicyValue.getPolicyRuleId(), storedPolicyValue.getPolicyValue());
            defaultPolicyValueMap.put(storedPolicyValue.getPolicyRuleId(), storedPolicyValue.getPolicyValue());
        }

        List<StoredPolicyValue> subOrgStoredPolicies = policyQueryRepository.getStoredSubOrgPolicyRules(companyCode,subOrgCode,userId)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "SubOrg policy Not found"));
        log.info("the subOrg policy list is fetched");

        Map<Integer, String> subOrgStoredPolicyMap = new HashMap<>();
        for(StoredPolicyValue storedPolicyValue: subOrgStoredPolicies) {
            subOrgStoredPolicyMap.put(storedPolicyValue.getPolicyRuleId(), storedPolicyValue.getPolicyValue());
            defaultPolicyValueMap.put(storedPolicyValue.getPolicyRuleId(), storedPolicyValue.getPolicyValue());

        }

        List<StoredPolicyValue> userStoredPolicies = policyQueryRepository.getStoredUserPolicyRules(userId)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "user policy Not found"));
        log.info("the user policy list is fetched");

        Map<Integer, String> userStoredPolicyMap = new HashMap<>();
        for(StoredPolicyValue storedPolicyValue: userStoredPolicies) {
            userStoredPolicyMap.put(storedPolicyValue.getPolicyRuleId(), storedPolicyValue.getPolicyValue());
            defaultPolicyValueMap.put(storedPolicyValue.getPolicyRuleId(), storedPolicyValue.getPolicyValue());
        }

        List<StoredPolicyRule> storedPolicyRules = new ArrayList<>();
        for (CompanyPolicyResponse companyPolicyResponse : companyList) {
            StoredPolicyRule storedPolicyRule = StoredPolicyRule.builder()
                    .policyRuleName(companyPolicyResponse.getPolicyName())
                    .policyRuleId(companyPolicyResponse.getPolicyRuleId())
                    .companyValue("1")
                    .defaultValue(companyPolicyResponse.getControlValue())
                    .subOrgValue(subOrgStoredPolicyMap.get(companyPolicyResponse.getPolicyRuleId()))
                    .companyValue(companyStoredPolicyMap.get(companyPolicyResponse.getPolicyRuleId()))
                    .userValue(userStoredPolicyMap.get(companyPolicyResponse.getPolicyRuleId()))
                    .finalValue(defaultPolicyValueMap.get(companyPolicyResponse.getPolicyRuleId()))
                    .build();
            storedPolicyRules.add(storedPolicyRule);
        }
        StoredPolicyRuleResponse storedPolicyRuleResponse = new StoredPolicyRuleResponse();
        storedPolicyRuleResponse.setUserId(userId);
        storedPolicyRuleResponse.setStoredPolicyRuleList(storedPolicyRules);
        return storedPolicyRuleResponse;
    }
}
