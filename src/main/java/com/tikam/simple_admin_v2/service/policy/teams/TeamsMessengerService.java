package com.tikam.simple_admin_v2.service.policy.teams;

import com.tikam.simple_admin_v2.annotation.validation.Teams;
import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.policy.CompanyPolicyResponse;
import com.tikam.simple_admin_v2.dto.policy.PolicyResponse;
import com.tikam.simple_admin_v2.dto.policy.PortalPolicyRequest;
import com.tikam.simple_admin_v2.dto.policy.PortalUserPolicyResponse;
import com.tikam.simple_admin_v2.exception.AdminException;
import com.tikam.simple_admin_v2.exception.ErrorCode;
import com.tikam.simple_admin_v2.repository.query.PolicyQueryRepository;
import com.tikam.simple_admin_v2.service.policy.PolicyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//@Teams
public class TeamsMessengerService extends PolicyService {
    protected TeamsMessengerService(PolicyQueryRepository policyQueryRepository) {
        super(policyQueryRepository);
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
}
