package com.tikam.simple_admin_v2.controller;

import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.policy.OrgPolicyRequest;
import com.tikam.simple_admin_v2.dto.policy.OrgPolicyTreeResponse;
import com.tikam.simple_admin_v2.dto.policy.PortalPolicyRequest;
import com.tikam.simple_admin_v2.dto.policy.PortalUserPolicyResponse;
import com.tikam.simple_admin_v2.service.policy.PolicyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
