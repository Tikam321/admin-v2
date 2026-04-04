package com.tikam.simple_admin_v2.dto.policy;

import lombok.Builder;

@Builder
public record StoredPolicyRule(int policyRuleId,String policyRuleName, String defaultValue,
                                       String companyValue, String subOrgValue, String userValue, String finalValue)
{
}
