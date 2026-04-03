package com.tikam.simple_admin_v2.dto.policy;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AddPolicyRequest {
    @NotNull(message = "policyRuleId cannot be null")
    private Integer policyRuleId;
    private String ruleText;
    private String englishRuleText;
    private String ruleTypeValue;
    private Boolean deliverClientYn;
    private Byte deliverTenantAdminYn;
    private Short policyGroupId;
    private String dataType;
    private String policyUnit;
    @NotEmpty(message = "permanentControlValue cannot be empty")
    private String permanentControlValue;
    private Byte orgPolicyRuleYn;
    private Byte securityGradePolicyRuleYn;
    private Byte userPolicyRuleYn;
    private Short policyAlignNo;

}
