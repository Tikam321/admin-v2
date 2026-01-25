package com.tikam.simple_admin_v2.dto.applink;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppLinkAddRequest {
    @NotBlank
    private String appName;
    @NotBlank
    private String appUrl;
    private String description;
    @NotBlank
    private String status;
}
