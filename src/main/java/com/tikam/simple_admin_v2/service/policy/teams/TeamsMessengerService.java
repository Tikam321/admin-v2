package com.tikam.simple_admin_v2.service.policy.teams;

import com.tikam.simple_admin_v2.annotation.validation.Teams;
import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.policy.PortalPolicyRequest;
import com.tikam.simple_admin_v2.dto.policy.PortalUserPolicyResponse;
import com.tikam.simple_admin_v2.exception.AdminException;
import com.tikam.simple_admin_v2.exception.ErrorCode;
import com.tikam.simple_admin_v2.repository.query.PolicyQueryRepository;
import com.tikam.simple_admin_v2.service.policy.PolicyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Teams
public class TeamsMessengerService extends PolicyService {
    protected TeamsMessengerService(PolicyQueryRepository policyQueryRepository) {
        super(policyQueryRepository);
    }

    @Override
    public APIResponse<PortalUserPolicyResponse> getUserPolicyForPortal(PortalPolicyRequest portalPolicyRequest) {
        throw new AdminException(ErrorCode.NOT_FOUND, "the user policy for portal is not found in teams");
    }
}
