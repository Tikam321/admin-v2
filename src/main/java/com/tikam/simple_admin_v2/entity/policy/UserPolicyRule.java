package com.tikam.simple_admin_v2.entity.policy;

import com.tikam.simple_admin_v2.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usr_plc_rl")
@IdClass(UserPolicyRuleId.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPolicyRule extends BaseDateTimeEntity {

    @Id
    @Column(name = "usr_id")
    private Long userId;

    @Id
    @Column(name = "plc_rl_id")
    private Integer policyRuleId;

    @Column(name = "ctrl_val")
    private String controlValue;
}
