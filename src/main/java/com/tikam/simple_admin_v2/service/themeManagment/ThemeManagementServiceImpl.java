package com.tikam.simple_admin_v2.service.themeManagment;

import com.tikam.simple_admin_v2.dto.CommonPageResponse;
import com.tikam.simple_admin_v2.dto.themeManagement.ThemeManagementListResponse;
import com.tikam.simple_admin_v2.dto.themeManagement.ThemeManagementResponse;
import com.tikam.simple_admin_v2.dto.themeManagement.ThemeVersionResponse;
import com.tikam.simple_admin_v2.entity.theme.ThemeManagement;
import com.tikam.simple_admin_v2.entity.theme.ThemeVersion;
import com.tikam.simple_admin_v2.repository.query.ThemeManagementQueryRepository;
import com.tikam.simple_admin_v2.repository.themeRepository.ThemeFileMetaDataRepository;
import com.tikam.simple_admin_v2.repository.themeRepository.ThemeManagementRepository;
import com.tikam.simple_admin_v2.repository.themeRepository.ThemeVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeManagementServiceImpl implements ThemeManagementService {
    private final ThemeManagementRepository themeManagementRepository;
    private final ThemeVersionRepository themeVersionRepository;
    private final ThemeFileMetaDataRepository themeFileMetaDataRepository;
    private final ThemeManagementQueryRepository themeManagementQueryRepository;

    @Override
    public ThemeManagementListResponse getThemeList(int page, int size) {
        // Handle 1-based pagination input
        int pageIndex = (page > 0) ? page - 1 : 0;
        PageRequest pageable = PageRequest.of(pageIndex, size);

        Page<ThemeManagement> themes = themeManagementRepository.findAll(pageable);
        List<ThemeManagementResponse> themeList = themes.getContent()
                .stream()
                .map(theme -> new ThemeManagementResponse().from(theme))
                .toList();

        return ThemeManagementListResponse.builder()
                .totalCount(themes.getTotalElements()) // Fix: Use getTotalElements() for total items
                .totalPages(themes.getTotalPages())
                .list(themeList)
                .page(themes.getNumber() + 1) // Return 1-based page
                .size(themes.getSize())
                .build();
    }

    @Override
    public CommonPageResponse<ThemeVersionResponse> getThemeVersionList(int page, int size, Long themeId) {
        // Handle 1-based pagination input
        int pageIndex = (page > 0) ? page - 1 : 0;
        Pageable pageRequest = PageRequest.of(pageIndex, size);

//        Page<ThemeVersion> paginatedThemeVersions = themeVersionRepository.findAllByThemeManagementId(themeId, pageRequest);
        Page<ThemeVersion> paginatedThemeVersions = themeManagementQueryRepository.findVersionsByThemeId(themeId, pageIndex, size);

        List<ThemeVersionResponse> themeVersionResponse = paginatedThemeVersions.getContent()
                .stream()
                .map(themeVersion -> new ThemeVersionResponse().from(themeVersion))
                .toList();

        return CommonPageResponse.<ThemeVersionResponse>builder()
                .list(themeVersionResponse)
                .page(paginatedThemeVersions.getNumber() + 1) // Return 1-based page
                .size(paginatedThemeVersions.getSize())
                .totalCount(paginatedThemeVersions.getTotalElements()) // Fix: Use getTotalElements()
                .build();
    }
}
