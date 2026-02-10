package com.tikam.simple_admin_v2.controller;

import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.CommonPageResponse;
import com.tikam.simple_admin_v2.dto.themeManagement.ThemeManagementListResponse;
import com.tikam.simple_admin_v2.dto.themeManagement.ThemeManagementResponse;
import com.tikam.simple_admin_v2.dto.themeManagement.ThemeVersionResponse;
import com.tikam.simple_admin_v2.service.themeManagment.ThemeManagementService;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/api/theme")
@RequiredArgsConstructor
public class ThemeController {

    private final  ThemeManagementService themeManagementService;

//    @Autowired
//    public ThemeController(ThemeManagementService themeManagementService) {
//        this.themeManagementService = themeManagementService;
//    }

    @GetMapping("/list")
    public ResponseEntity<ThemeManagementListResponse> getThemes(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {
        
        ThemeManagementListResponse themeList = themeManagementService.getThemeList(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(themeList);
    }

    @GetMapping("/version/list")
    public APIResponse<CommonPageResponse> getVersions(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @Nonnull @RequestParam(required = true) Long themeId) {

        return APIResponse.ok(themeManagementService.getThemeVersionList(page, size, themeId));
    }

}
