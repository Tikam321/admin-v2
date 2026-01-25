package com.tikam.simple_admin_v2.entity.applink;

import com.tikam.simple_admin_v2.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "applink")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AppLink extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appId;

    @Column(nullable = false)
    private String appName;

    @Column(nullable = false)
    private String appUrl;

    private String description;

    @Column(nullable = false)
    private String status;
}
