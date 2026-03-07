package com.tikam.simple_admin_v2.dto.device;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeviceState {
    private String companyName;
    private String companyCode;
    private String subOrgCode;
    private String subOrgName;
    private String deptName;
    private String localName;
    private String globalName;
    private String position;
    private String appVersion;
    private String osType;
    private String osVersion;


    public static DeviceState from(DeviceStateDto deviceStateDto) {
        // complete builder pattern here
        return DeviceState.builder()
                .companyName(deviceStateDto.getCompanyName())
                .companyCode(deviceStateDto.getCompanyCode())
                .subOrgCode(deviceStateDto.getSubOrgCode())
                .subOrgName(deviceStateDto.getSubOrgName())
                .deptName(deviceStateDto.getDeptName())
                .localName(deviceStateDto.getLocalName())
                .position(deviceStateDto.getPosition())
                .globalName(deviceStateDto.getGlobalName())
                .appVersion(deviceStateDto.getAppVersion())
                .osType(deviceStateDto.getOsType())
                .osVersion(deviceStateDto.getOsVersion())
                .build();

    }

}
