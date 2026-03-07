package com.tikam.simple_admin_v2.service.policy.messenger;

import com.tikam.simple_admin_v2.annotation.validation.Messenger;
import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.policy.PortalPolicyRequest;
import com.tikam.simple_admin_v2.dto.policy.PortalUserPolicyDto;
import com.tikam.simple_admin_v2.dto.policy.PortalUserPolicyResponse;
import com.tikam.simple_admin_v2.entity.User;
import com.tikam.simple_admin_v2.entity.policy.UserPolicyRule;
import com.tikam.simple_admin_v2.entity.policy.UserPolicyRuleId;
import com.tikam.simple_admin_v2.repository.UserRepository;
import com.tikam.simple_admin_v2.repository.policy.UserPolicyRuleRepository;
import com.tikam.simple_admin_v2.repository.query.PolicyQueryRepository;
import com.tikam.simple_admin_v2.service.policy.PolicyService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@Messenger
public class MessengerPolicyService extends PolicyService {
    private final UserPolicyRuleRepository userPolicyRuleRepository;
    private final UserRepository userRepository;

    protected MessengerPolicyService(PolicyQueryRepository policyQueryRepository,
                                     UserPolicyRuleRepository userPolicyRuleRepository, UserRepository userRepository) {
        super(policyQueryRepository);
        this.userPolicyRuleRepository = userPolicyRuleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public APIResponse<PortalUserPolicyResponse> getUserPolicyForPortal(PortalPolicyRequest portalPolicyRequest) {
        Integer policyId = portalPolicyRequest.policyId();
        List<User> users = getUsersFromEpIds(portalPolicyRequest.epId());
        System.out.println("user list " + users);
        List<UserPolicyRuleId>  userPolicyRuleIds = users.stream().map(user-> new UserPolicyRuleId(user.getUserId(), policyId)).toList();

        List<UserPolicyRule> userPolicyRules = userPolicyRuleRepository.findAllById(userPolicyRuleIds);
        System.out.println("userPolicyRules " + userPolicyRules.get(0).getUserId() + " " + userPolicyRules.get(0).getControlValue());
        Map<Long,String> userPolicyMap = new HashMap<>();
        for(UserPolicyRule userPolicyRule: userPolicyRules) {
            userPolicyMap.put(userPolicyRule.getUserId(), userPolicyRule.getControlValue());
        }
        System.out.println(userPolicyMap);
        List<PortalUserPolicyDto> portalUserResponse = users.stream()
                .map(user -> new PortalUserPolicyDto(user.getUserEpId(),
                        userPolicyMap.get(user.getUserId()))).toList();
        System.out.println(portalUserResponse);
        return APIResponse.ok(new PortalUserPolicyResponse(policyId,portalUserResponse));
    }


    private List<User> getUsersFromEpIds(@NotEmpty List<String> epIds) {
        List<User> userIds = userRepository.findByUserEpIdIn(epIds);
        return userIds;
    }
}
