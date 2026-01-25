package com.tikam.simple_admin_v2.dto.applink;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppLinkUpdateRequest {
    @NotNull
    private Long appId;
    private String appName;
    private String appUrl;
    private String description;
    private String status;
}
