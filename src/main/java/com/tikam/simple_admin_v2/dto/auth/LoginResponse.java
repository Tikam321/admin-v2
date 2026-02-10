package com.tikam.simple_admin_v2.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String token;
    private String refreshToken;
    private Long userId;
    private String email;
    private String role; // e.g., "ADMIN"
}
