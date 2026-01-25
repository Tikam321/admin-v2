package com.tikam.simple_admin_v2.repository.applink;

import com.tikam.simple_admin_v2.entity.applink.AppLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppLinkRepository extends JpaRepository<AppLink, Long> {
}
