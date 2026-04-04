package com.tikam.simple_admin_v2.dto.policy;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class StoredPolicyValue {
    private int policyRuleId;
    private String policyValue;

    @QueryProjection
    public StoredPolicyValue(int policyRuleId, String policyValue) {
        this.policyRuleId = policyRuleId;
        this.policyValue = policyValue;
    }
}
