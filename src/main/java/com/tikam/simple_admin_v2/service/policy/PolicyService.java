package com.tikam.simple_admin_v2.service.policy;

import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.policy.*;
import com.tikam.simple_admin_v2.enums.PolicyTreeLevel;
import com.tikam.simple_admin_v2.exception.AdminException;
import com.tikam.simple_admin_v2.exception.ErrorCode;
import com.tikam.simple_admin_v2.repository.query.PolicyQueryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional(readOnly = true)
public abstract class PolicyService {
    protected final PolicyQueryRepository policyQueryRepository;

    protected PolicyService(PolicyQueryRepository policyQueryRepository) {
        this.policyQueryRepository = policyQueryRepository;
    }

    public OrgPolicyTreeResponse gtOrgPolicyTree(OrgPolicyRequest orgPolicyRequest) {
        PolicyTreeLevel policyTreeLevel = PolicyTreeLevel.from(orgPolicyRequest.getTreeLevel());
        if (policyTreeLevel == null) {
            log.info("invalid treeLevel: {}",orgPolicyRequest.getTreeLevel());
            throw new AdminException(ErrorCode.ARGUMENT_IS_INVALID, "the tree level is not valid");
        }

        String companyCode = orgPolicyRequest.getCompanyCode();
        String suborgCode = orgPolicyRequest.getSubOrgCode();

        //we will existence of companyCide and suborgCode
        Map<Integer,String > defaultPolicy = policyQueryRepository.getDefaultPolicyMap(orgPolicyRequest.getPolicyIds());
        Map<Integer,String > companyPolicy = policyQueryRepository.getCompanyPolicy(companyCode,orgPolicyRequest.getPolicyIds());
        Map<Integer,String > subOrgPolicy = policyTreeLevel == PolicyTreeLevel.SUBORG ?
                policyQueryRepository.getSubOrgPolicy(companyCode, suborgCode, orgPolicyRequest.getPolicyIds()) :
                new HashMap<>();

        return OrgPolicyTreeResponse.builder().defaultPolicy(defaultPolicy).companyPolicy(companyPolicy).subOrgPolicy(subOrgPolicy).build();
    }


    public abstract APIResponse<PortalUserPolicyResponse> getUserPolicyForPortal(@Valid PortalPolicyRequest portalPolicyRequest);
    public  abstract List<CompanyPolicyResponse> getCompanyList(int companyId);
    public abstract CompanyPolicyResponse getCompanyPolicy(int companyId, int policyId);
    public abstract PolicyResponse getDefaultPolicy(int policyLicenseId, int policyId);


}
