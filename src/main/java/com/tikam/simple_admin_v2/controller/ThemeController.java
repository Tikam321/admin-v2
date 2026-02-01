package com.tikam.simple_admin_v2.controller;

import com.tikam.simple_admin_v2.dto.themeManagement.ThemeManagementListResponse;
import com.tikam.simple_admin_v2.dto.themeManagement.ThemeManagementResponse;
import com.tikam.simple_admin_v2.service.themeManagment.ThemeManagementService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/admin/api/theme")
public class ThemeController {

    private  ThemeManagementService themeManagementService;

    @Autowired
    public ThemeController(ThemeManagementService themeManagementService) {
        this.themeManagementService = themeManagementService;
    }

    @GetMapping("/list")
    public ResponseEntity<ThemeManagementListResponse> getThemes(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {
        
        ThemeManagementListResponse themeList = themeManagementService.getThemeList(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(themeList);
    }
}
