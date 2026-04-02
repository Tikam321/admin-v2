package com.tikam.simple_admin_v2.dto.user;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class LoginInfoDto {

    private String userEpId;
    private String companyName;
    private String suborgCode;
    private String departmentCode;
    private String localName;
    private Long createTime;


    @QueryProjection
    public LoginInfoDto(String userEpId, Long createTime, String localName, String departmentCode, String suborgCode, String companyName) {
        this.userEpId = userEpId;
        this.createTime = createTime;
        this.localName = localName;
        this.departmentCode = departmentCode;
        this.suborgCode = suborgCode;
        this.companyName = companyName;
    }

}
