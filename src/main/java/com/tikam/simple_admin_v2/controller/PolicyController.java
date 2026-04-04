package com.tikam.simple_admin_v2.controller;

import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.policy.*;
import com.tikam.simple_admin_v2.service.policy.PolicyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@RestController
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @PostMapping("/policy/org/tree")
    public APIResponse<OrgPolicyTreeResponse> getOrgPolicyTree(@RequestBody @Valid OrgPolicyRequest orgPolicyRequest) {
        return APIResponse.ok(policyService.gtOrgPolicyTree(orgPolicyRequest));
    }

    @PostMapping("/policy/portal/user")
    public APIResponse<PortalUserPolicyResponse> getUserPolicyForPortal(@RequestBody @Valid PortalPolicyRequest portalPolicyRequest) {
        return policyService.getUserPolicyForPortal(portalPolicyRequest);
    }
    //company level policies

    @GetMapping("/workspace/policy/list")
    public List<CompanyPolicyResponse> getCompanyLevelPolicies(@RequestParam Integer companyId) {
        // Implementation for fetching company level policies
       return policyService.getCompanyList(companyId);
    }

    @GetMapping("/workspace/policy")
    public CompanyPolicyResponse getCompanyLevelPolicies(@RequestParam Integer companyId,@RequestParam Integer policyLicenseId) {
        // Implementation for fetching company level policies
        return  policyService.getCompanyPolicy(companyId, policyLicenseId);
    }

    @GetMapping("/workspace/default/policy")
    public APIResponse<PolicyResponse> getDefaultPolicy(@RequestParam Integer policyLicenseId, @RequestParam Integer policyRuleId) {
        // Implementation for fetching company level policies
        return  APIResponse.ok(policyService.getDefaultPolicy(policyLicenseId, policyRuleId));
    }

    //add/update policy
    @PostMapping("/workspace/policy/add")
    public APIResponse addPolicy(@RequestBody @Valid AddPolicyRequest policyRequest) {
        // Implementation for fetching company level policies
        return policyService.addPolicy(policyRequest);
    }

    // delete policy by policy id
    // TODO: delete from userpolicyRule table
    // TODO: delete from orgpolicyRule
    // TODO: delete from policyRule
    // TODO: delete from company license policy rule
    @DeleteMapping("/workspace/policy/delete")
    public APIResponse deletePolicy(@RequestParam Integer policyId) {
        // Implementation for fetching company level policies
        // implement later when you have time
        return APIResponse.deleted();
    }

    @GetMapping("/workspace/policy/stored")
    public APIResponse<StoredPolicyRuleResponse> allUserPolicyGet(
            @RequestParam String companyCode, @RequestParam String subOrgCode, @RequestParam Long userId) {
       return APIResponse.ok(policyService.allUserPolicyGet(userId, companyCode,subOrgCode));
    }











}
