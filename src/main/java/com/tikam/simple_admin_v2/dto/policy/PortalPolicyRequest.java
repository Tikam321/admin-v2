package com.tikam.simple_admin_v2.dto.policy;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PortalPolicyRequest(
        @NotNull Integer policyId,
        @NotEmpty List<String> epId
) {
}
