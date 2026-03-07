package com.tikam.simple_admin_v2.entity.policy;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserPolicyRuleId implements Serializable {
    private Long userId;
    private Integer policyRuleId;
}
