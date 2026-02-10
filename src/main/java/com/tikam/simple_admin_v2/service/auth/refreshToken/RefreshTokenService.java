package com.tikam.simple_admin_v2.service.auth.refreshToken;

import com.tikam.simple_admin_v2.dto.auth.RefreshTokenRequest;
import com.tikam.simple_admin_v2.entity.RefreshToken;

public interface RefreshTokenService {
    String createRefreshToken(Long userId);
    Boolean verifyExpiration(RefreshToken refreshToken);
    Boolean deleteByUserId(Long useId);
     String generateJwtToken(RefreshTokenRequest refreshTokenRequest);
}
