package com.tikam.simple_admin_v2.dto.policy.userPolicyDto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PolicyUserResponse {
    Long userId;
    String localName;
    String userEpId;
    String company;
    String department;
    Long createTime;
    Long updateTime ;

    @QueryProjection
    public PolicyUserResponse(Long userId, Long updateTime, Long createTime, String company, String localName, String department, String userEpId) {
        this.userId = userId;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.company = company;
        this.localName = localName;
        this.department = department;
        this.userEpId = userEpId;
    }
}
