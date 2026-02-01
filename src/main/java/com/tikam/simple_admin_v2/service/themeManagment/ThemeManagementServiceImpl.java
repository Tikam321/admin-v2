package com.tikam.simple_admin_v2.service.themeManagment;

import com.tikam.simple_admin_v2.dto.themeManagement.ThemeManagementListResponse;
import com.tikam.simple_admin_v2.dto.themeManagement.ThemeManagementResponse;
import com.tikam.simple_admin_v2.entity.theme.ThemeManagement;
import com.tikam.simple_admin_v2.repository.themeRepository.ThemeManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeManagementServiceImpl implements ThemeManagementService{
    private final ThemeManagementRepository themeManagementRepository;

    @Override
    public ThemeManagementListResponse getThemeList(int page,int size) {
        PageRequest pageable = PageRequest.ofSize(size).withPage(page-1);
        Page<ThemeManagement> themes = themeManagementRepository.findAll(pageable);
        List<ThemeManagementResponse> themeList = themes.getContent()
                .stream()
                .map(theme -> new ThemeManagementResponse().from(theme))
                .toList();

        return ThemeManagementListResponse.builder()
                .totalCount(themes.getSize())
                .totalPages(themes.getTotalPages())
                .list(themeList)
                .page(themes.getNumber()+1)
                .size(themes.getSize())
                .build();
    }

}
