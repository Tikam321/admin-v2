package com.tikam.simple_admin_v2.entity.policy;

import com.tikam.simple_admin_v2.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cmpny_lcs_plc_rl")
@IdClass(CompanyLicensePolicyRuleId.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLicensePolicyRule extends BaseDateTimeEntity {

    @Id
    @Column(name = "cmpny_lcs_plc_id")
    private Integer companyLcsPolicyId;

    @Id
    @Column(name = "plc_rl_id")
    private Integer policyRuleId;

    @Column(name = "ctrl_val")
    private String controlValue;
}
