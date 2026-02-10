package com.tikam.simple_admin_v2.service.themeManagment;

import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.CommonPageResponse;
import com.tikam.simple_admin_v2.dto.themeManagement.ThemeManagementListResponse;
import com.tikam.simple_admin_v2.dto.themeManagement.ThemeVersionResponse;

public interface ThemeManagementService {

    ThemeManagementListResponse getThemeList(int page, int size);

    CommonPageResponse<ThemeVersionResponse> getThemeVersionList(int page, int size, Long themeId);

}
