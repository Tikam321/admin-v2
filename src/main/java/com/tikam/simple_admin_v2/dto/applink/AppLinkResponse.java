package com.tikam.simple_admin_v2.dto.applink;

import com.tikam.simple_admin_v2.entity.applink.AppLink;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppLinkResponse {
    private Long appId;
    private String appName;
    private String appUrl;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AppLinkResponse from(AppLink appLink) {
        return AppLinkResponse.builder()
                .appId(appLink.getAppId())
                .appName(appLink.getAppName())
                .appUrl(appLink.getAppUrl())
                .description(appLink.getDescription())
                .status(appLink.getStatus())
                .createdAt(appLink.getCreatedAt())
                .updatedAt(appLink.getUpdatedAt())
                .build();
    }
}
