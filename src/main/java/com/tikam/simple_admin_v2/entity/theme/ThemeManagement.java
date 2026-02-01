package com.tikam.simple_admin_v2.entity.theme;

import com.tikam.simple_admin_v2.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "theme_management")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ThemeManagement extends BaseDateTimeEntity {
    @Id
    @Column(name = "theme_management_id")
    private Long themeManagementId;

    @Column(name = "theme_name")
    private String themeName;

    @Column(name = "description")
    private String description;

    @Column(name = "isActive")
    private String isActive;
}
