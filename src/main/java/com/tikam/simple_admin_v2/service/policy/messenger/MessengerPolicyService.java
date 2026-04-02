package com.tikam.simple_admin_v2.service.policy.messenger;

import com.tikam.simple_admin_v2.annotation.validation.Messenger;
import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.policy.*;
import com.tikam.simple_admin_v2.entity.User;
import com.tikam.simple_admin_v2.entity.policy.CompanyLicensePolicy;
import com.tikam.simple_admin_v2.entity.policy.UserPolicyRule;
import com.tikam.simple_admin_v2.entity.policy.UserPolicyRuleId;
import com.tikam.simple_admin_v2.exception.AdminException;
import com.tikam.simple_admin_v2.exception.ErrorCode;
import com.tikam.simple_admin_v2.repository.UserRepository;
import com.tikam.simple_admin_v2.repository.policy.UserPolicyRuleRepository;
import com.tikam.simple_admin_v2.repository.query.PolicyQueryRepository;
import com.tikam.simple_admin_v2.service.policy.PolicyService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
//@Messenger
@Primary
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

    public List<CompanyPolicyResponse> getCompanyList(int companyId) {
        List<CompanyPolicyResponse> companyPolicyList = policyQueryRepository.getCompanyList(companyId)
                        .orElseThrow(() ->new AdminException(ErrorCode.NOT_FOUND, "Company policy not found"));
        System.out.println(companyPolicyList);
        return companyPolicyList;
    }

    public CompanyPolicyResponse getCompanyPolicy(int companyId, int policyId) {
        CompanyPolicyResponse companyPolicyList = policyQueryRepository.getCompanyPolicy(companyId,policyId)
                .orElseThrow(() ->new AdminException(ErrorCode.NOT_FOUND, "Company policy not found"));
        System.out.println(companyPolicyList);
        return companyPolicyList;
    }

    @Override
    public PolicyResponse getDefaultPolicy(int policyLicenseId, int policyId) {
        PolicyResponse policyRuleValue = policyQueryRepository.getPolicyRuleValueCheck(policyId)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "Company policy not found"));

        String defaultControlValue = policyQueryRepository.getDefaultPolicyValue(policyId, policyLicenseId)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "Default policy value not found"));

        policyRuleValue.setPermanentControlValue(defaultControlValue);

        return policyRuleValue;
    }

}
