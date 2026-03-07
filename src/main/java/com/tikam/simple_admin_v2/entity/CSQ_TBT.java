package com.tikam.simple_admin_v2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "csq_tbt")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CSQ_TBT {

    @Id
    @Column(name = "ep_id")
    private String employeeId;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "sub_org_code")
    private String subOrgCode;

    @Column(name = "department_code")
    private String departmentCode;

    @Column(name = "company_name")
    private String companyName;
}
