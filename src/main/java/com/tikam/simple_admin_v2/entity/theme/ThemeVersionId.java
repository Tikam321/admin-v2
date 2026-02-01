package com.tikam.simple_admin_v2.entity.theme;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class ThemeVersionId implements Serializable {
    private Long themeVersionId;
    private Long themeManagementId;
}
