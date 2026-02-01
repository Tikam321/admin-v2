package com.tikam.simple_admin_v2.service.auth;

import com.tikam.simple_admin_v2.dto.auth.LoginRequest;
import com.tikam.simple_admin_v2.dto.auth.LoginResponse;
import com.tikam.simple_admin_v2.entity.User;
import com.tikam.simple_admin_v2.entity.admin.AdminUser;
import com.tikam.simple_admin_v2.exception.AdminException;
import com.tikam.simple_admin_v2.exception.ErrorCode;
import com.tikam.simple_admin_v2.repository.UserRepository;
import com.tikam.simple_admin_v2.repository.admin.AdminUserRepository;
import com.tikam.simple_admin_v2.util.JwtUtils;
import com.tikam.simple_admin_v2.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final AdminUserRepository adminUserRepository;
    private final JwtUtils jwtUtils;

    public LoginResponse login(LoginRequest request) {
        // 1. Find User by Email
        User user = userRepository.findByEmailAddress(request.email())
                .orElseThrow(() -> new AdminException(ErrorCode.BAD_REQUEST, "Invalid UserName or Password"));

        // 2. Verify Password
        System.out.println("user password " + user.getPassword());
        System.out.println("user salt " +user.getSalt());
        System.out.println("requests password " + request.password());

        boolean isValid = PasswordUtils.verifyPassword(
                request.password(),
                user.getSalt(),
                user.getPassword()
        );

        if (!isValid) {
            log.error("Invalid credentials");
            throw new AdminException(ErrorCode.BAD_REQUEST, "Invalid credentials");
        }

        // 3. Check Admin Privileges
        AdminUser adminUser = adminUserRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new AdminException(ErrorCode.FORBIDDEN, "Access Denied: Not an Admin"));

        if (!"ACTIVE".equals(adminUser.getStatus())) {
            log.error("Access Denied: Admin account is inactive");
            throw new AdminException(ErrorCode.FORBIDDEN, "Access Denied: Admin account is inactive");
        }

        // 4. Generate Token
        String token = jwtUtils.generateToken(user.getUserId(), user.getEmailAddress());

        return LoginResponse.builder()
                .token(token)
                .userId(user.getUserId())
                .email(user.getEmailAddress())
                .role(adminUser.getAdminType().name())
                .build();
    }
}
