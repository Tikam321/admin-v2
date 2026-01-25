package com.tikam.simple_admin_v2.dto.admin;


import com.tikam.simple_admin_v2.entity.admin.AdminUser;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class AdminUserUpdateRequest {

    private String userName;

    @Email
    private String email;

    private AdminUser.AdminType adminType;

    private String privilegeValue;

    private String status;
}