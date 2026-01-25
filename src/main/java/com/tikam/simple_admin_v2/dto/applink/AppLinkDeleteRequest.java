package com.tikam.simple_admin_v2.dto.applink;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppLinkDeleteRequest {
    @NotNull
    private Long appId;
}
