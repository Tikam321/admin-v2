package com.tikam.simple_admin_v2.controller;

import com.tikam.simple_admin_v2.dto.APIResponse;

import com.tikam.simple_admin_v2.dto.admin.AdminUserCreateRequest;
import com.tikam.simple_admin_v2.dto.admin.AdminUserResponse;
import com.tikam.simple_admin_v2.dto.admin.AdminUserUpdateRequest;
import com.tikam.simple_admin_v2.entity.admin.AdminUser;
import com.tikam.simple_admin_v2.service.admin.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin-users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;
    @PostMapping
    public ResponseEntity<APIResponse<AdminUserResponse>> createAdminUser(@Valid @RequestBody AdminUserCreateRequest request) {
        AdminUserResponse response = adminUserService.createAdminUser(request);
        return new ResponseEntity<>(APIResponse.created(response), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<AdminUserResponse>> getAdminUserById(@PathVariable Long id) {
        AdminUserResponse response = adminUserService.getAdminUserById(id);
        return ResponseEntity.ok(APIResponse.ok(response));
    }

    @GetMapping("/user-id/{userId}")
    public ResponseEntity<APIResponse<AdminUserResponse>> getAdminUserByUserId(@PathVariable Long userId) {
        AdminUserResponse response = adminUserService.getAdminUserByUserId(userId);
        return ResponseEntity.ok(APIResponse.ok(response));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<AdminUserResponse>>> getAllAdminUsers() {
        List<AdminUserResponse> response = adminUserService.getAllAdminUsers();
        return ResponseEntity.ok(APIResponse.ok(response));
    }

    @GetMapping("/type/{adminType}")
    public ResponseEntity<APIResponse<List<AdminUserResponse>>> getAdminUsersByType(@PathVariable AdminUser.AdminType adminType) {
        List<AdminUserResponse> response = adminUserService.getAdminUsersByType(adminType);
        return ResponseEntity.ok(APIResponse.ok(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<AdminUserResponse>> updateAdminUser(@PathVariable Long id, @Valid @RequestBody AdminUserUpdateRequest request) {
        AdminUserResponse response = adminUserService.updateAdminUser(id, request);
        return ResponseEntity.ok(APIResponse.updated(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdminUser(@PathVariable Long id) {
        adminUserService.deleteAdminUser(id);
        return ResponseEntity.noContent().build();
    }
}