package com.tikam.simple_admin_v2.repository;

import com.tikam.simple_admin_v2.entity.UserDevice;
import com.tikam.simple_admin_v2.entity.UserDeviceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, UserDeviceId> {
}
