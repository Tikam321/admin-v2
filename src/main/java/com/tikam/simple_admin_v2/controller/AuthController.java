package com.tikam.simple_admin_v2.controller;

import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.auth.LoginRequest;
import com.tikam.simple_admin_v2.dto.auth.LoginResponse;
import com.tikam.simple_admin_v2.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(APIResponse.ok(response));
    }
}
