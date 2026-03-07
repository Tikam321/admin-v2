package com.tikam.simple_admin_v2.dto.device;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceStateRequest {
    private String companyCode;
    private String subOrgCode;
    @NotEmpty
    private List<String> deviceType;

    @NotEmpty
    private List<String> osTypes;

    private String appVersion;

    @NotNull
    @Size(min = 10,max = 100)
    private String reason;

}
