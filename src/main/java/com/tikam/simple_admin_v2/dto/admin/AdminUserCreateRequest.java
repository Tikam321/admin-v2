package com.tikam.simple_admin_v2.dto.admin;


import com.tikam.simple_admin_v2.entity.admin.AdminUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminUserCreateRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String userName;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private AdminUser.AdminType adminType;

    private String privilegeValue;

    private String status = "ACTIVE";
}
