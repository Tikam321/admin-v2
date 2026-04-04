package com.tikam.simple_admin_v2.dto.policy.userPolicyDto;

import com.tikam.simple_admin_v2.dto.policy.StoredPolicyValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserPolicyRequest {
    private List<Long> userIds;
    private List<StoredPolicyValue> policyValueList;

}
