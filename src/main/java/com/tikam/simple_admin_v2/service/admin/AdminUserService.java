
package com.tikam.simple_admin_v2.service.admin;

import com.tikam.simple_admin_v2.dto.admin.AdminUserCreateRequest;
import com.tikam.simple_admin_v2.dto.admin.AdminUserResponse;
import com.tikam.simple_admin_v2.dto.admin.AdminUserUpdateRequest;
import com.tikam.simple_admin_v2.entity.admin.AdminUser;
import com.tikam.simple_admin_v2.exception.ResourceNotFoundException;
import com.tikam.simple_admin_v2.repository.admin.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;

    public AdminUserResponse createAdminUser(AdminUserCreateRequest request) {
        if (adminUserRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("User ID already exists");
        }
        if (adminUserRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        AdminUser adminUser = AdminUser.builder()
                .userId(request.getUserId())
                .userName(request.getUserName())
                .email(request.getEmail())
                .adminType(request.getAdminType())
                .privilegeValue(request.getPrivilegeValue())
                .status(request.getStatus())
                .build();

        AdminUser savedUser = adminUserRepository.save(adminUser);
        return AdminUserResponse.from(savedUser);
    }

    @Transactional(readOnly = true)
    public AdminUserResponse getAdminUserById(Long id) {
        AdminUser adminUser = adminUserRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("AdminUser", "id", id));
        return AdminUserResponse.from(adminUser);
    }

    @Transactional(readOnly = true)
    public AdminUserResponse getAdminUserByUserId(Long userId) {
        AdminUser adminUser = adminUserRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("AdminUser", "userId", userId));
        return AdminUserResponse.from(adminUser);
    }
    @Transactional(readOnly = true)
    public List<AdminUserResponse> getAllAdminUsers() {
        return adminUserRepository.findAll().stream()
                .map(AdminUserResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdminUserResponse> getAdminUsersByType(AdminUser.AdminType adminType) {
        return adminUserRepository.findByAdminType(adminType).stream()
                .map(AdminUserResponse::from)
                .collect(Collectors.toList());
    }

    public AdminUserResponse updateAdminUser(Long id, AdminUserUpdateRequest request) {
        AdminUser adminUser = adminUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AdminUser", "id", id));

        if (request.getEmail() != null && !request.getEmail().equals(adminUser.getEmail())) {
            if (adminUserRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
            adminUser.setEmail(request.getEmail());
        }

        if (request.getUserName() != null) {
            adminUser.setUserName(request.getUserName());
        }
        if (request.getAdminType() != null) {
            adminUser.setAdminType(request.getAdminType());
        }
        if (request.getPrivilegeValue() != null) {
            adminUser.setPrivilegeValue(request.getPrivilegeValue());
        }
        if (request.getStatus() != null) {
            adminUser.setStatus(request.getStatus());
        }

        AdminUser updatedUser = adminUserRepository.save(adminUser);
        return AdminUserResponse.from(updatedUser);
    }
    public void deleteAdminUser(Long id) {
        if (!adminUserRepository.existsById(id)) {
            throw new ResourceNotFoundException("AdminUser", "id", id);
        }
        adminUserRepository.deleteById(id);
    }
}
