package com.tikam.simple_admin_v2.dto.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;



public record RefreshTokenRequest(@NotEmpty(message = "refresh token is required") String refreshToken) {
}
