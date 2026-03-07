package com.tikam.simple_admin_v2.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDeviceId implements Serializable {
    private Long userId;
    private String dvcId;
}
