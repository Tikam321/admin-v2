package com.tikam.simple_admin_v2.dto.policy;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
@Setter
@AllArgsConstructor
@Getter
public class PolicyResponse {
    private Integer policyRuleId;
    private String ruleText;
    private String englishRuleText;
    private String ruleTypeValue;
    private Boolean deliverClientYn;
    private Byte deliverTenantAdminYn;
    private Short policyGroupId;
    private String dataType;
    private String policyUnit;
    private String permanentControlValue;
    private Byte orgPolicyRuleYn;
    private Byte securityGradePolicyRuleYn;
    private Byte userPolicyRuleYn;
    private Short policyAlignNo;

    @QueryProjection
    public PolicyResponse(String dataType, Byte userPolicyRuleYn, Byte securityGradePolicyRuleYn,
                          String ruleTypeValue, String ruleText, String policyUnit,
                          Integer policyRuleId, Short policyGroupId, Short policyAlignNo,
                          String permanentControlValue, Byte orgPolicyRuleYn, String englishRuleText,
                          Byte deliverTenantAdminYn, Boolean deliverClientYn) {
        this.dataType = dataType;
        this.userPolicyRuleYn = userPolicyRuleYn;
        this.securityGradePolicyRuleYn = securityGradePolicyRuleYn;
        this.ruleTypeValue = ruleTypeValue;
        this.ruleText = ruleText;
        this.policyUnit = policyUnit;
        this.policyRuleId = policyRuleId;
        this.policyGroupId = policyGroupId;
        this.policyAlignNo = policyAlignNo;
        this.permanentControlValue = permanentControlValue;
        this.orgPolicyRuleYn = orgPolicyRuleYn;
        this.englishRuleText = englishRuleText;
        this.deliverTenantAdminYn = deliverTenantAdminYn;
        this.deliverClientYn = deliverClientYn;
    }
}
