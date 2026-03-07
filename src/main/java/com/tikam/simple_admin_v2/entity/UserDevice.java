package com.tikam.simple_admin_v2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_device")
@IdClass(UserDeviceId.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDevice {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "dvc_id")
    private String dvcId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "active_status")
    private String activeStatus;
}
