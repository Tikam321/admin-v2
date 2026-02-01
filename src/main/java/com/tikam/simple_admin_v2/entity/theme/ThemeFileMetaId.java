package com.tikam.simple_admin_v2.entity.theme;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class ThemeFileMetaId implements Serializable {
    private Long themeFileMetaId;

    private Long themeVersionId;
    private Long themeManagementId;
}
