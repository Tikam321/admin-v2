package com.tikam.simple_admin_v2.service.policy.messenger;

import com.tikam.simple_admin_v2.annotation.validation.Messenger;
import com.tikam.simple_admin_v2.dto.policy.StoredPolicyRuleResponse;
import com.tikam.simple_admin_v2.dto.policy.StoredPolicyValue;
import com.tikam.simple_admin_v2.dto.policy.userPolicyDto.AddUserPolicyRequest;
import com.tikam.simple_admin_v2.dto.policy.userPolicyDto.PolicyUserResponse;
import com.tikam.simple_admin_v2.entity.policy.UserPolicyRule;
import com.tikam.simple_admin_v2.exception.AdminException;
import com.tikam.simple_admin_v2.exception.ErrorCode;
import com.tikam.simple_admin_v2.repository.policy.UserPolicyRuleRepository;
import com.tikam.simple_admin_v2.repository.query.UserPolicyQueryRepository;
import com.tikam.simple_admin_v2.service.policy.UserPolicyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Messenger
public class MessengerUserPolicyService implements UserPolicyService {

    private final UserPolicyRuleRepository userPolicyRuleRepository;
    private final UserPolicyQueryRepository userPolicyQueryRepository;

    @Transactional
    public boolean addUserPolicy(AddUserPolicyRequest addUserPolicyRequest) {
        List<Long> userIds = addUserPolicyRequest.getUserIds();
        List<StoredPolicyValue> userPolicyRules = addUserPolicyRequest.getPolicyValueList();
        log.info("fetch userIds and storedPolicy from request payload");
        if (userIds.isEmpty() || userPolicyRules.isEmpty()) {
            throw new AdminException(ErrorCode.NOT_FOUND, "either userIds or policyValueList is empty");
        }

        for (Long userId : userIds) {
            for (StoredPolicyValue storedPolicyValue : userPolicyRules) {
                UserPolicyRule userPolicyRule = UserPolicyRule.builder()
                        .policyRuleId(storedPolicyValue.getPolicyRuleId())
                        .userId(userId)
                        .controlValue(storedPolicyValue.getPolicyValue())
                        .build();
                userPolicyRuleRepository.save(userPolicyRule);
            }
        }
        log.info("all teh policies for users are added successfully.");
        return true;
    }

    public List<PolicyUserResponse> getUserPolicyListByUser(String companyCode, String subOrgCode) {
        List<PolicyUserResponse> userList = userPolicyQueryRepository
                .getUserList(companyCode, subOrgCode).orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "user not found"));
        log.info("the user list is fetched successfully from companyCode:{} and subOrgCode:{}",companyCode, subOrgCode);
        return userList;
    }

    public List<PolicyUserResponse> getUserPolicyListByPolicy(String companyCode, String subOrgCode,int policyRuleId) {
        List<PolicyUserResponse> userList = userPolicyQueryRepository
                .getSearchUserByPolicy(companyCode, subOrgCode,policyRuleId).orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "user not found"));
        log.info("the user list is fetched successfully from companyCode: {} and subOrgCode: {} and policyId: {}",companyCode, subOrgCode,policyRuleId);
        return userList;
    }

}
