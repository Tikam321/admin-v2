package com.tikam.simple_admin_v2.entity.theme;

import com.tikam.simple_admin_v2.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "theme_file_metadata")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ThemeFileMetaId.class)
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ThemeFileMetaData extends BaseDateTimeEntity {
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "theme_file_meta_id")
    private Long themeFileMetaId;

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "theme_version_id")
    private Long themeVersionId;

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "theme_management_id")
    private Long themeManagementId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

}
