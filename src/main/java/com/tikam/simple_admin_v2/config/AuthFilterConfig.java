package com.tikam.simple_admin_v2.config;

import com.tikam.simple_admin_v2.dto.auth.UserInfo;
import com.tikam.simple_admin_v2.entity.admin.AdminUser;
import com.tikam.simple_admin_v2.exception.AdminException;
import com.tikam.simple_admin_v2.exception.ErrorCode;
import com.tikam.simple_admin_v2.repository.admin.AdminUserRepository;
import com.tikam.simple_admin_v2.repository.query.ThemeManagementQueryRepository;
import com.tikam.simple_admin_v2.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthFilterConfig extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final AdminUserRepository adminUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        // 1. check for token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // 2. validate token
            if (jwtUtils.validateToken(token)) {
                Long userId = jwtUtils.getUserIdFromToken(token);
                String email = jwtUtils.getEmailFromToken(token);
                //3 .load admin details
                AdminUser adminUser = adminUserRepository.findByUserId(userId).orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "user don't have admin access"));

                if (adminUser != null && "ACTIVE".equals(adminUser.getStatus())) {
                    // 4. create user info
                    UserInfo userInfo = UserInfo.builder().role(adminUser.getAdminType().name())
                            .userId(userId)
                            .email(email)
                            .build();
                    // 5. set context
                    UserContext.setContext(userInfo);
                }

            }
        }
        try {
            // 6. continue the request
            filterChain.doFilter(request, response);
        } finally {
            // 7.  cleanup to avid memory leak
            UserContext.clearContext();
        }
    }
}
