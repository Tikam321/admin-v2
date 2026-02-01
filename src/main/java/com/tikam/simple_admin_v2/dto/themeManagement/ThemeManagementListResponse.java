package com.tikam.simple_admin_v2.dto.themeManagement;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class ThemeManagementListResponse {
    List<ThemeManagementResponse> list;
    int totalCount;
    int page;
    int size;
    int totalPages;

}
