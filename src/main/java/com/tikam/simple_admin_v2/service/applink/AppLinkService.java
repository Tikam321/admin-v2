package com.tikam.simple_admin_v2.service.applink;

import com.tikam.simple_admin_v2.dto.applink.*;
import com.tikam.simple_admin_v2.entity.applink.AppLink;
import com.tikam.simple_admin_v2.exception.AdminException;
import com.tikam.simple_admin_v2.exception.ErrorCode;
import com.tikam.simple_admin_v2.repository.applink.AppLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppLinkService {

    private final AppLinkRepository appLinkRepository;

    public AppLinkResponseList getAppLinkList(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<AppLink> appLinkPage = appLinkRepository.findAll(pageRequest);

        List<AppLinkResponse> list = appLinkPage.getContent().stream()
                .map(AppLinkResponse::from)
                .collect(Collectors.toList());

        return AppLinkResponseList.builder()
                .list(list)
                .totalCount(appLinkPage.getTotalElements())
                .page(appLinkPage.getNumber())
                .size(appLinkPage.getSize())
                .totalPages(appLinkPage.getTotalPages())
                .nextCursor(1)
                .build();
    }

    public AppLinkCurserResponse getAppLinkListByKeySet(int size, long curser) {
        Pageable pageable = PageRequest.of(0, size );
        List<AppLink> appLinkPage = appLinkRepository.findNextPage(curser, pageable);

        List<AppLinkResponse> list = appLinkPage.stream()
                .map(AppLinkResponse::from)
                .collect(Collectors.toList());

        // 2. The Next Cursor
        Long nextCurser = null;
        if (!list.isEmpty()) {
            nextCurser = list.get(list.size()-1).getAppId();
        }

        // 3. Has Next (Optional but helpful)
        // A simple trick: Ask for 'size + 1' in the repository.
        // If you get back 'size + 1' items, you know there is more.
        // Then remove the extra item before sending the list.
        boolean hasNext =  list.size() == size;

        return AppLinkCurserResponse.builder()
                .list(list)
                .hasNext(hasNext)
                .nextCurser(nextCurser)
                .build();
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
