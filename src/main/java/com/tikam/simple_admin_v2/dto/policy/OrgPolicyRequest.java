package com.tikam.simple_admin_v2.dto.policy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrgPolicyRequest {
    private String treeLevel;
    private String companyCode;
    private String subOrgCode;
    private List<Integer> policyIds;

}
