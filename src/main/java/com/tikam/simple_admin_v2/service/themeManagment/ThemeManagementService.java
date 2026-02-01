package com.tikam.simple_admin_v2.service.themeManagment;

import com.tikam.simple_admin_v2.dto.themeManagement.ThemeManagementListResponse;
import com.tikam.simple_admin_v2.dto.themeManagement.ThemeManagementResponse;

import java.util.List;

public interface ThemeManagementService {

    ThemeManagementListResponse getThemeList(int page, int size);
}
