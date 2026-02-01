package com.tikam.simple_admin_v2.dto.themeManagement;

import com.tikam.simple_admin_v2.entity.theme.ThemeManagement;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThemeManagementResponse {
    private Long themeManagementId;
    private String themeName;
    private String themeDescription;
    private String status;
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;

    public ThemeManagementResponse from(ThemeManagement themeManagement) {
        return ThemeManagementResponse.builder()
                .themeManagementId(themeManagement.getThemeManagementId())
                .themeDescription(themeManagement.getDescription())
                .status(themeManagement.getIsActive())
                .createdAt(themeManagement.getCreatedAt())
                .updatedAt(themeManagement.getUpdatedAt()).build();
    }


}
