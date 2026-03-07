package com.tikam.simple_admin_v2.entity.policy;

import com.fasterxml.jackson.databind.JsonSerializable;
import com.tikam.simple_admin_v2.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cmpny_lcs_plc")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLicensePolicy extends BaseDateTimeEntity {
    @Id
    @Column(name = "cmpny_lcs_plc_id")
    private Integer companylcsPolicyId;

    @Column(name = "lcs_id", nullable = false)
    private Integer lcsId;

    @Column(name = "cmpny_id", nullable = false)
    private Integer companyId;

    @Column(name = "plc_typ_cd")
    private Integer policyTypeCode;

    @Column(name = "plc_nm")
    private String policyName;

    @Column(name = "plc_desc")
    private String policyDescription;

    @Column(name = "use_yn")
    private Integer useYn;
}
