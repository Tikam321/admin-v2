package com.tikam.simple_admin_v2.repository.themeRepository;

import com.tikam.simple_admin_v2.entity.theme.ThemeFileMetaData;
import com.tikam.simple_admin_v2.entity.theme.ThemeFileMetaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeFileMetaDataRepository extends JpaRepository<ThemeFileMetaData, ThemeFileMetaId> {

}
