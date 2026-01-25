package com.tikam.simple_admin_v2.controller;

import com.tikam.simple_admin_v2.dto.applink.*;
import com.tikam.simple_admin_v2.service.applink.AppLinkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api")
@RequiredArgsConstructor
@Validated
public class AppLinkController {

    private final AppLinkService appLinkService;

    @GetMapping("/applink/list")
    public AppLinkResponseList getAppLinkList() {
        return appLinkService.getAppLinkList();
    }

    @GetMapping("/applink")
    public AppLinkResponse getAppLinkDetail(@RequestParam Long appId) {
        return appLinkService.getAppLinkDetail(appId);
    }

    @PostMapping("/applink/add")
    public AppLinkResponse addAppLink(@Valid @RequestBody AppLinkAddRequest request) {
        System.out.println("conteorller " + request);
        return appLinkService.addAppLink(request);
    }

    @PostMapping("/applink/update")
    public AppLinkResponse updateAppLink(@Valid @RequestBody AppLinkUpdateRequest request) {
        return appLinkService.updateAppLink(request);
    }

    @PostMapping("/applink/delete")
    public boolean deleteAppLink(@Valid @RequestBody AppLinkDeleteRequest request) {
        return appLinkService.deleteAppLink(request);
    }
}
