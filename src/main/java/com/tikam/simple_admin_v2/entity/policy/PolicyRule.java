package com.tikam.simple_admin_v2.entity.policy;

import com.tikam.simple_admin_v2.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "plc_rl")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyRule extends BaseDateTimeEntity {
    @Id
    @Column(name = "plc_rl_id")
    private Integer policyRuleId;

    @Column(name = "rl_txt", length = 512)
    private String ruleText;

    @Column(name = "en_rl_txt", length = 512)
    private String englishRuleText;

    @Column(name = "rl_typ_val", length = 256)
    private String ruleTypeValue;

    @Column(name = "deliv_clnt_yn")
    private Boolean deliverClientYn;

    @Column(name = "deliv_tnt_admin_yn", nullable = false)
    @ColumnDefault("0")
    private Byte deliverTenantAdminYn;

    @Column(name = "plc_grp_id")
    private Short policyGroupId;

    @Column(name = "data_type", length = 20)
    private String dataType;

    @Column(name = "plc_unit", length = 20)
    private String policyUnit;

    @Column(name = "pmt_ctrl_val", length = 500)
    private String permanentControlValue;

    @Column(name = "org_plc_rl_yn")
    private Byte orgPolicyRuleYn;

    @Column(name = "secu_grd_plc_rl_yn")
    private Byte securityGradePolicyRuleYn;

    @Column(name = "usr_plc_rl_yn")
    private Byte userPolicyRuleYn;

    @Column(name = "plc_align_no", nullable = false)
    @ColumnDefault("0")
    private Short policyAlignNo;

}
