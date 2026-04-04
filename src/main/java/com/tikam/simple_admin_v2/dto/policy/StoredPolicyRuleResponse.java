package com.tikam.simple_admin_v2.dto.policy;

import lombok.*;

import java.util.List;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoredPolicyRuleResponse {
    private List<StoredPolicyRule> storedPolicyRuleList;
    private Long userId;

}
