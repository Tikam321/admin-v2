package com.tikam.simple_admin_v2.dto.themeManagement;

import com.tikam.simple_admin_v2.entity.theme.ThemeVersion;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThemeVersionResponse {
    private Long themeVersionId;
    private Long themeId;
    private String versionName;
    private String changeLog;
    private LocalDate  createDate;
    private LocalDate updateDate;

    public ThemeVersionResponse from(ThemeVersion themeVersion) {
        return ThemeVersionResponse.builder()
                .themeId(themeVersion.getThemeManagementId())
                .themeVersionId(themeVersion.getThemeVersionId())
                .changeLog(themeVersion.getChangeLog())
                .versionName(themeVersion.getVersionName())
                .createDate(themeVersion.getCreatedAt().toLocalDate())
                .updateDate(themeVersion.getUpdatedAt().toLocalDate())
                .build();
    }


}
