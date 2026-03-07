package com.tikam.simple_admin_v2.repository;

import com.tikam.simple_admin_v2.entity.CSQ_TBT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsqTbtRepository extends JpaRepository<CSQ_TBT, String> {
}
