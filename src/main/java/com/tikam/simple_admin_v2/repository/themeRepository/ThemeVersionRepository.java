package com.tikam.simple_admin_v2.repository.themeRepository;

import com.tikam.simple_admin_v2.entity.theme.ThemeVersion;
import com.tikam.simple_admin_v2.entity.theme.ThemeVersionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeVersionRepository extends JpaRepository<ThemeVersion, ThemeVersionId> {
}
