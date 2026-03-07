package com.tikam.simple_admin_v2.repository;

import com.tikam.simple_admin_v2.entity.MsgPrxConn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsgPrxConnRepository extends JpaRepository<MsgPrxConn, String> {
}
