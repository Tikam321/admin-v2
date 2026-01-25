package com.tikam.simple_admin_v2.service.applink;

import com.tikam.simple_admin_v2.dto.applink.*;
import com.tikam.simple_admin_v2.entity.applink.AppLink;
import com.tikam.simple_admin_v2.exception.AdminException;
import com.tikam.simple_admin_v2.exception.ErrorCode;
import com.tikam.simple_admin_v2.repository.applink.AppLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppLinkService {

    private final AppLinkRepository appLinkRepository;

    public AppLinkResponseList getAppLinkList() {
        List<AppLinkResponse> list = appLinkRepository.findAll().stream()
                .map(AppLinkResponse::from)
                .collect(Collectors.toList());
        return AppLinkResponseList.builder().list(list).build();
    }

    public AppLinkResponse getAppLinkDetail(Long appId) {
        AppLink appLink = appLinkRepository.findById(appId)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "AppLink not found with id: " + appId));
        return AppLinkResponse.from(appLink);
    }

    @Transactional
    public AppLinkResponse addAppLink(AppLinkAddRequest request) {
        AppLink appLink = AppLink.builder()
                .appName(request.getAppName())
                .appUrl(request.getAppUrl())
                .description(request.getDescription())
                .status(request.getStatus())
                .build();
        System.out.println(appLink);
        AppLink saved = appLinkRepository.save(appLink);
        return AppLinkResponse.from(saved);
    }

    @Transactional
    public AppLinkResponse updateAppLink(AppLinkUpdateRequest request) {
        AppLink appLink = appLinkRepository.findById(request.getAppId())
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "AppLink not found with id: " + request.getAppId()));

        if (request.getAppName() != null) appLink.setAppName(request.getAppName());
        if (request.getAppUrl() != null) appLink.setAppUrl(request.getAppUrl());
        if (request.getDescription() != null) appLink.setDescription(request.getDescription());
        if (request.getStatus() != null) appLink.setStatus(request.getStatus());

        return AppLinkResponse.from(appLink);
    }

    @Transactional
    public boolean deleteAppLink(AppLinkDeleteRequest request) {
        if (!appLinkRepository.existsById(request.getAppId())) {
            throw new AdminException(ErrorCode.NOT_FOUND, "AppLink not found with id: " + request.getAppId());
        }
        appLinkRepository.deleteById(request.getAppId());
        return true;
    }
}
