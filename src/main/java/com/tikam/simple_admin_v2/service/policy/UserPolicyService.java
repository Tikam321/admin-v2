package com.tikam.simple_admin_v2.service.policy;

import com.tikam.simple_admin_v2.dto.policy.userPolicyDto.AddUserPolicyRequest;
import com.tikam.simple_admin_v2.dto.policy.userPolicyDto.PolicyUserResponse;

import java.util.List;

public interface UserPolicyService {
    boolean addUserPolicy(AddUserPolicyRequest addUserPolicyRequest);
    List<PolicyUserResponse> getUserPolicyListByUser(String companyCode, String subOrgCode);
    List<PolicyUserResponse> getUserPolicyListByPolicy(String companyCode, String subOrgCode,int policyRuleId);
}
