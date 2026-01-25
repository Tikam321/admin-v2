package com.tikam.simple_admin_v2.dto.admin;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminUserAndIpAddRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Integer adminType;
    @NotNull
    private String privilegeValue;
    private LocalDate expiredDateTime;
}
