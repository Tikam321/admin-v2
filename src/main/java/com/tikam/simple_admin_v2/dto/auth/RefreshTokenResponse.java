package com.tikam.simple_admin_v2.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenResponse {
    private String token;
    private String refresh_token;
}
