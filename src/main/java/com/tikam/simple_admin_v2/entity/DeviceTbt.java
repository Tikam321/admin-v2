package com.tikam.simple_admin_v2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "device_tbt")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTbt {

    @Id
    @Column(name = "dvc_id")
    private String dvcId;

    @Column(name = "imei")
    private String imei;

    @Column(name = "modl_nm")
    private String modelName;

    @Column(name = "os_type_code")
    private Integer osTypeCode;

    @Column(name = "os_version")
    private String osVersion;

    @Column(name = "app_version")
    private String appVersion;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "createtime")
    private LocalDateTime createTime;
}
