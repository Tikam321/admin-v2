package com.tikam.simple_admin_v2.entity.theme;


import com.tikam.simple_admin_v2.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "theme_version")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@IdClass(ThemeVersionId.class)
public class ThemeVersion extends BaseDateTimeEntity {
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "version_id")
    private Long themeVersionId;

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "theme_management_id")
    private Long themeManagementId;

    @Column(name = "version_name")
    private String versionName;

    @Column(name = "change_log")
    private String changeLog;
}
