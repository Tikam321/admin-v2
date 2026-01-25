package com.tikam.simple_admin_v2.entity.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserId implements Serializable {
    private Long userId;
    private Integer adminType;
    private String privilegeValue;
}
