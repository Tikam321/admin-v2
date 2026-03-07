package com.tikam.simple_admin_v2.dto.device;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeviceStateDto {
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

    @QueryProjection

    public DeviceStateDto(String companyName, String companyCode, String subOrgCode, String subOrgName, String deptName, String localName, String position, String globalName,
                          String appVersion, String osType, String osVersion) {
        this.companyName = companyName;
        this.companyCode = companyCode;
        this.subOrgCode = subOrgCode;
        this.subOrgName = subOrgName;
        this.deptName = deptName;
        this.localName = localName;
        this.position = position;
        this.globalName = globalName;
        this.appVersion = appVersion;
        this.osType = osType;
        this.osVersion = osVersion;
    }
}
