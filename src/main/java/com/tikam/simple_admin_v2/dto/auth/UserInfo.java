package com.tikam.simple_admin_v2.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    private Long userId;
    private String email;
    private String role;

}
