package com.tikam.simple_admin_v2.dto;

import com.tikam.simple_admin_v2.document.PolicyHistory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PolicyHistoryRequest {
    @NotBlank
    private String username;
    @NotNull
    private PolicyHistory.TargetCategory targetCategory;
    @NotBlank
    private String targetName;
    @NotBlank
    private String policyName;
    private String prevValue;
    private String nextValue;
    @NotNull
    private PolicyHistory.Action action;
}
