package com.tikam.simple_admin_v2.repository.admin;


import com.tikam.simple_admin_v2.entity.admin.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByUserId(Long userId);
    Optional<AdminUser> findByEmail(String email);
    List<AdminUser> findByAdminType(AdminUser.AdminType adminType);
    List<AdminUser> findByStatus(String status);
    boolean existsByEmail(String email);
    boolean existsByUserId(Long userId);
}