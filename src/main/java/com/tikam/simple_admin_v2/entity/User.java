package com.tikam.simple_admin_v2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "usr")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    private Long userId;

    private String companyId;

    private String employeeNumber;

    private String globalName;

    private String localName;

    private String nickname;

    private String emailAddress;

    private String singleId;

    private String countryName;

    private String primaryPhoneNumber;

    private String businessPhoneNumber;

    private String voipPhoneNumber;

    private String departmentName;

    private String departmentCode;

    // Added Password and Salt fields for Authentication
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String salt;

    private Long createdUnixTime;

    private Long updatedUnixTime;
}
