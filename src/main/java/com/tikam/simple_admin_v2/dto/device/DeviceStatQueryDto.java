package com.tikam.simple_admin_v2.dto.device;

import com.tikam.simple_admin_v2.enums.DeviceOs;
import com.tikam.simple_admin_v2.enums.DeviceType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class DeviceStatQueryDto {
    private String companyCode;
    private String subOrgCode;
    private List<Integer> deviceType;
    private List<Integer> osTypes;
    private String appVersion;
    private String reason;


    public static DeviceStatQueryDto from(DeviceStateRequest deviceStateRequest) {
        List<Integer> deviceType = deviceStateRequest.getDeviceType().stream()
                .map(type -> DeviceType.from(type).getCode()).toList();
        List<Integer> osTypes = deviceStateRequest.getOsTypes().stream()
                .map(value -> DeviceOs.from(value).getCode()).toList();

        return DeviceStatQueryDto.builder()
                .appVersion(deviceStateRequest.getAppVersion())
                .companyCode(deviceStateRequest.getCompanyCode())
                .subOrgCode(deviceStateRequest.getSubOrgCode())
                .deviceType(deviceType)
                .osTypes(osTypes)
                .reason(deviceStateRequest.getReason())
                .build();
    }

}
