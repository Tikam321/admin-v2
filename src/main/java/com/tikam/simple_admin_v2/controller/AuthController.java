package com.tikam.simple_admin_v2.controller;

import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.auth.LoginRequest;
import com.tikam.simple_admin_v2.dto.auth.LoginResponse;
import com.tikam.simple_admin_v2.dto.auth.RefreshTokenRequest;
import com.tikam.simple_admin_v2.service.auth.AuthService;
import com.tikam.simple_admin_v2.service.auth.refreshToken.RefreshTokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(APIResponse.ok(response));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshToken) {
        return ResponseEntity.status(HttpStatus.CREATED).body(refreshTokenService.generateJwtToken(refreshToken));
    }
}
