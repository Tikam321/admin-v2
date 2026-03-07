package com.tikam.simple_admin_v2.repository.policy;

import com.tikam.simple_admin_v2.entity.policy.OrgPolicyRule;
import com.tikam.simple_admin_v2.entity.policy.OrgPolicyRuleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgPolicyRuleRepository extends JpaRepository<OrgPolicyRule, OrgPolicyRuleId> {
}
