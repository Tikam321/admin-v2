package com.tikam.simple_admin_v2.entity.policy;

import com.tikam.simple_admin_v2.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "org_plc_rl")
@IdClass(OrgPolicyRuleId.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgPolicyRule extends BaseDateTimeEntity {
    @Id
    @Column(name = "comp_cd")
    private String companyCode;

    @Id
    @Column(name = "suborg_cd")
    private String subOrgCode;

    @Id
    @Column(name = "plc_rl_id")
    private Integer policyRuleId;

    @Column(name = "ctrl_val")
    private String controlValue;

}
