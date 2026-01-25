package com.tikam.simple_admin_v2.dto.admin;


import com.tikam.simple_admin_v2.entity.admin.AdminUser;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AdminUserResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String email;
    private AdminUser.AdminType adminType;
    private String privilegeValue;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AdminUserResponse from(AdminUser adminUser) {
        return AdminUserResponse.builder()
                .id(adminUser.getId())
                .userId(adminUser.getUserId())
                .userName(adminUser.getUserName())
                .email(adminUser.getEmail())
                .adminType(adminUser.getAdminType())
                .privilegeValue(adminUser.getPrivilegeValue())
                .status(adminUser.getStatus())
                .createdAt(adminUser.getCreatedAt())
                .updatedAt(adminUser.getUpdatedAt())
                .build();
    }
}