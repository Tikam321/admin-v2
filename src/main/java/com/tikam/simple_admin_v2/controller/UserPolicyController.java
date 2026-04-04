package com.tikam.simple_admin_v2.controller;

import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.policy.userPolicyDto.AddUserPolicyRequest;
import com.tikam.simple_admin_v2.dto.policy.userPolicyDto.PolicyUserResponse;
import com.tikam.simple_admin_v2.service.policy.UserPolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/workspace/policy/user")
@RequiredArgsConstructor
public class UserPolicyController {
    private final UserPolicyService userPolicyService;

    @GetMapping("/list")
    public List<PolicyUserResponse> getUserPolicyList(@RequestParam String companyCode, @RequestParam String subOrgCode) {
        return userPolicyService.getUserPolicyListByUser(companyCode, subOrgCode);
    }

    @GetMapping("/search/ruleId")
    public List<PolicyUserResponse> getUserPolicyListByRuleId(
            @RequestParam String companyCode, @RequestParam String subOrgCode, @RequestParam int policyRuleId) {
        return userPolicyService.getUserPolicyListByPolicy(companyCode, subOrgCode, policyRuleId);
    }
    @PostMapping("/add")
    public APIResponse userPolicyAdd(@RequestBody @Valid AddUserPolicyRequest addUserPolicyRequest) {
        return APIResponse.added(userPolicyService.addUserPolicy(addUserPolicyRequest));
    }

    // TODO: Delete user
    // TODO: userDetail
    // TODO: userpolicyByDetail



}
