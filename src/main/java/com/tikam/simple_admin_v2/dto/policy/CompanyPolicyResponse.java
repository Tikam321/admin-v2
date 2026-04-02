package com.tikam.simple_admin_v2.dto.policy;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyPolicyResponse {
        private Integer companyLicensePolicyId;
        private Integer policyRuleId;
        private Integer policyTypeCode;
        private String policyName;
        private String policyDescription;
        private String controlValue;
        private String language;

        // Getters and Setters

    @QueryProjection
    public CompanyPolicyResponse(Integer companyLicensePolicyId, Integer policyRuleId, Integer policyTypeCode, String policyName, String policyDescription, String language, String controlValue) {
        this.companyLicensePolicyId = companyLicensePolicyId;
        this.policyRuleId = policyRuleId;
        this.policyTypeCode = policyTypeCode;
        this.policyName = policyName;
        this.policyDescription = policyDescription;
        this.language = language;
        this.controlValue = controlValue;
    }
}
