package com.tikam.simple_admin_v2.dto.policy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Builder
@Getter
@Setter
public class OrgPolicyTreeResponse {
    private Map<Integer, String> defaultPolicy;
    private Map<Integer, String> companyPolicy;
    private Map<Integer, String> subOrgPolicy;

}
