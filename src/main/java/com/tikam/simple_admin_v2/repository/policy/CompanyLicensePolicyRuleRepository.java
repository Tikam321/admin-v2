package com.tikam.simple_admin_v2.repository.policy;

import com.tikam.simple_admin_v2.entity.policy.CompanyLicensePolicyRule;
import com.tikam.simple_admin_v2.entity.policy.CompanyLicensePolicyRuleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyLicensePolicyRuleRepository  extends JpaRepository<CompanyLicensePolicyRule, CompanyLicensePolicyRuleId> {
}
