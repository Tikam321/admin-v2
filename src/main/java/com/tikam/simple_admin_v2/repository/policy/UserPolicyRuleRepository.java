package com.tikam.simple_admin_v2.repository.policy;

import com.tikam.simple_admin_v2.entity.policy.UserPolicyRule;
import com.tikam.simple_admin_v2.entity.policy.UserPolicyRuleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPolicyRuleRepository extends JpaRepository<UserPolicyRule, UserPolicyRuleId> {


}
