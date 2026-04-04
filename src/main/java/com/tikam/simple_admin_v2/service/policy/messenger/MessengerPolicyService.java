package com.tikam.simple_admin_v2.service.policy.messenger;

import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.policy.*;
import com.tikam.simple_admin_v2.entity.User;
import com.tikam.simple_admin_v2.entity.policy.*;
import com.tikam.simple_admin_v2.exception.AdminException;
import com.tikam.simple_admin_v2.exception.ErrorCode;
import com.tikam.simple_admin_v2.repository.UserRepository;
import com.tikam.simple_admin_v2.repository.policy.CompanyLicensePolicyRuleRepository;
import com.tikam.simple_admin_v2.repository.policy.PolicyRuleRepository;
import com.tikam.simple_admin_v2.repository.policy.UserPolicyRuleRepository;
import com.tikam.simple_admin_v2.repository.query.PolicyQueryRepository;
import com.tikam.simple_admin_v2.service.policy.PolicyService;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
//@Messenger
@Primary
@Slf4j
public class MessengerPolicyService extends PolicyService {
    private final UserPolicyRuleRepository userPolicyRuleRepository;
    private final UserRepository userRepository;
    private final CompanyLicensePolicyRuleRepository companyLicensePolicyRuleRepository;
    private final PolicyRuleRepository policyRuleRepository;


    private final static int COMPANY_DEFAULT_LICENSE_ID = 101;

    protected MessengerPolicyService(PolicyQueryRepository policyQueryRepository,
                                     UserPolicyRuleRepository userPolicyRuleRepository, UserRepository userRepository, CompanyLicensePolicyRuleRepository companyLicensePolicyRule, PolicyRuleRepository policyRuleRepository) {
        super(policyQueryRepository);
        this.userPolicyRuleRepository = userPolicyRuleRepository;
        this.userRepository = userRepository;
        this.companyLicensePolicyRuleRepository = companyLicensePolicyRule;
        this.policyRuleRepository = policyRuleRepository;
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

    @Transactional
    @Override
    public APIResponse addPolicy(AddPolicyRequest policyRequest) {

        PolicyRule policyRule = PolicyRule
                .builder()
                .orgPolicyRuleYn(policyRequest.getOrgPolicyRuleYn())
                .policyRuleId(policyRequest.getPolicyRuleId())
                .userPolicyRuleYn(policyRequest.getUserPolicyRuleYn())
                .securityGradePolicyRuleYn(policyRequest.getSecurityGradePolicyRuleYn())
                .policyAlignNo(policyRequest.getPolicyAlignNo())
                .policyGroupId(policyRequest.getPolicyGroupId())
                .policyUnit(policyRequest.getPolicyUnit())
                .ruleText(policyRequest.getRuleText())
                .ruleTypeValue(policyRequest.getRuleTypeValue())
                .dataType(policyRequest.getDataType())
                .deliverClientYn(policyRequest.getDeliverClientYn())
                .permanentControlValue(policyRequest.getPermanentControlValue())
                .englishRuleText(policyRequest.getEnglishRuleText())
                .deliverTenantAdminYn(policyRequest.getDeliverTenantAdminYn())
                .build();
        policyRuleRepository.save(policyRule);
        CompanyLicensePolicyRule companyLicensePolicyRule = CompanyLicensePolicyRule
                .builder()
                .policyRuleId(policyRequest.getPolicyRuleId())
                .companyLcsPolicyId(COMPANY_DEFAULT_LICENSE_ID)
                .controlValue(policyRequest.getPermanentControlValue())
                .build();
        try {
            // Save both entities
             companyLicensePolicyRuleRepository.save(companyLicensePolicyRule);
            log.info("Policy added to companyLicensePolicyRule table");

            PolicyRule savedPolicy = policyRuleRepository.save(policyRule);
            log.info("Policy added to policyRule table with ID: {}", savedPolicy.getPolicyRuleId());

            return APIResponse.added(true);
        }  catch (Exception e) {
            log.error("Failed to add policy: {}", policyRequest.getPolicyRuleId(), e);
            throw new AdminException(ErrorCode.POLICY_ADD_FAILED);
        }
    }

    public StoredPolicyRuleResponse allUserPolicyGet(Long userId, String companyCode,String subOrgCode) {
        List<CompanyPolicyResponse> companyList = policyQueryRepository.getCompanyList(1)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "Company policy not found"));
        log.info("the default company policy list is fetched");
        List<StoredPolicyValue> companyStoredPolicies = policyQueryRepository.getStoredCompanyPolicyRules(companyCode, userId)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "Company policy not found"));
        log.info("the company policy list is fetched");

        Map<Integer, String> defaultPolicyValueMap = new HashMap<>();

        Map<Integer, String> companyStoredPolicyMap = new HashMap<>();
        for(StoredPolicyValue storedPolicyValue: companyStoredPolicies) {
            companyStoredPolicyMap.put(storedPolicyValue.getPolicyRuleId(), storedPolicyValue.getPolicyValue());
            defaultPolicyValueMap.put(storedPolicyValue.getPolicyRuleId(), storedPolicyValue.getPolicyValue());
        }

        List<StoredPolicyValue> subOrgStoredPolicies = policyQueryRepository.getStoredSubOrgPolicyRules(companyCode,subOrgCode,userId)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "SubOrg policy Not found"));
        log.info("the subOrg policy list is fetched");

        Map<Integer, String> subOrgStoredPolicyMap = new HashMap<>();
        for(StoredPolicyValue storedPolicyValue: subOrgStoredPolicies) {
            subOrgStoredPolicyMap.put(storedPolicyValue.getPolicyRuleId(), storedPolicyValue.getPolicyValue());
            defaultPolicyValueMap.put(storedPolicyValue.getPolicyRuleId(), storedPolicyValue.getPolicyValue());

        }

        List<StoredPolicyValue> userStoredPolicies = policyQueryRepository.getStoredUserPolicyRules(userId)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "user policy Not found"));
        log.info("the user policy list is fetched");

        Map<Integer, String> userStoredPolicyMap = new HashMap<>();
        for(StoredPolicyValue storedPolicyValue: userStoredPolicies) {
            userStoredPolicyMap.put(storedPolicyValue.getPolicyRuleId(), storedPolicyValue.getPolicyValue());
            defaultPolicyValueMap.put(storedPolicyValue.getPolicyRuleId(), storedPolicyValue.getPolicyValue());
        }

        List<StoredPolicyRule> storedPolicyRules = new ArrayList<>();
        for (CompanyPolicyResponse companyPolicyResponse : companyList) {
            StoredPolicyRule storedPolicyRule = StoredPolicyRule.builder()
                    .policyRuleName(companyPolicyResponse.getPolicyName())
                    .policyRuleId(companyPolicyResponse.getPolicyRuleId())
                    .companyValue("1")
                    .defaultValue(companyPolicyResponse.getControlValue())
                    .subOrgValue(subOrgStoredPolicyMap.get(companyPolicyResponse.getPolicyRuleId()))
                    .companyValue(companyStoredPolicyMap.get(companyPolicyResponse.getPolicyRuleId()))
                    .userValue(userStoredPolicyMap.get(companyPolicyResponse.getPolicyRuleId()))
                    .finalValue(defaultPolicyValueMap.get(companyPolicyResponse.getPolicyRuleId()))
                    .build();
            storedPolicyRules.add(storedPolicyRule);
        }
        StoredPolicyRuleResponse storedPolicyRuleResponse = new StoredPolicyRuleResponse();
        storedPolicyRuleResponse.setUserId(userId);
        storedPolicyRuleResponse.setStoredPolicyRuleList(storedPolicyRules);
        return storedPolicyRuleResponse;
    }

}
