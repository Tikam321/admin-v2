package com.tikam.simple_admin_v2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "msq_prx_conn")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgPrxConn {

    @Id
    @Column(name = "dvc_id")
    private String dvcId;

    @Column(name = "msg_prx_ip")
    private String msgPrxIp;

    @Column(name = "conn_ums")
    private LocalDateTime connUms;

    @Column(name = "disconn_time")
    private LocalDateTime disconnTime;

    @Column(name = "last_access_utms")
    private LocalDateTime lastAccessUtms;
}
