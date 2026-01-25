package com.tikam.simple_admin_v2.entity.admin;


import com.tikam.simple_admin_v2.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin_user")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AdminUser extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long userId;

    @Column(nullable = false, length = 100)
    private String userName;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminType adminType;

    @Column(length = 50)
    private String privilegeValue;

    @Column(nullable = false, length = 20)
    private String status;

    public enum AdminType {
        SUPER_ADMIN, ADMIN, VIEWER
    }
}
