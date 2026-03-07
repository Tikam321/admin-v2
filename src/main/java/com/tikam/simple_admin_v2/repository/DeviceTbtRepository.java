package com.tikam.simple_admin_v2.repository;

import com.tikam.simple_admin_v2.entity.DeviceTbt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceTbtRepository extends JpaRepository<DeviceTbt, String> {
}
