package com.tikam.simple_admin_v2.dto.admin;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class UserInfoDto {
    private  Long userId;
    private Long companyId;
    private String employeeNumber;
    private String globalName;
    private String localName;
    private String nickname;
    private String emailAddress;
    private String singleId;

    @QueryProjection
    public UserInfoDto(String employeeNumber, Long userId, Long companyId, String globalName, String localName, String nickname, String emailAddress, String singleId) {
        this.employeeNumber = employeeNumber;
        this.userId = userId;
        this.companyId = companyId;
        this.globalName = globalName;
        this.localName = localName;
        this.nickname = nickname;
        this.emailAddress = emailAddress;
        this.singleId = singleId;
    }
}
