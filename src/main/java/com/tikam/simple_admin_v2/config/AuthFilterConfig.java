package com.tikam.simple_admin_v2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tikam.simple_admin_v2.dto.APIResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthFilterConfig extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final AdminUserRepository adminUserRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        // 1. check for token
        try {
            if (verifyAuthHeader(authHeader)) {
                String token = authHeader.substring(7);
                // 2. validate token
                if (isJwtFormat(token) && jwtUtils.validateToken(token) ) {
                    System.out.println("the token is verified " + jwtUtils.getUserIdFromToken(token));
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
            } else {
                logger.error("invalid auth token");
                throw new AdminException(ErrorCode.INVALID_TOKEN, "invalid token");
            }
            // 6. continue the request
            filterChain.doFilter(request, response);
        } catch (AdminException ex) {
            handleSecurityException(response, ex.getErrorCode().getStatus(), ex.getMessage());
        } catch (Exception ex) {
            handleSecurityException(response, HttpStatus.UNAUTHORIZED, ex.getMessage());
        } finally {
            // 7.  cleanup to avid memory leak
            UserContext.clearContext();
        }
    }

    private boolean isJwtFormat(String token) {
        String[] parts = token.split("\\.");
        return parts.length == 3;
    }

    private void handleSecurityException(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // Use your APIResponse DTO for consistent error format
        APIResponse<Object> errorResponse = APIResponse.builder().message(message).success(false).build();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private boolean verifyAuthHeader(String authHeader) {
        if (authHeader != null && authHeader.length() > 7 && authHeader.startsWith("Bearer ")) {
            return true;
        }
        return false;
    }
}
