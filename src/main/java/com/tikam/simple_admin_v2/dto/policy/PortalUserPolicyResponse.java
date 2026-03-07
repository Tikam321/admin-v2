package com.tikam.simple_admin_v2.dto.policy;

import java.util.List;

public record PortalUserPolicyResponse( Integer policyId,
        List<PortalUserPolicyDto> userPolicy) {

}
