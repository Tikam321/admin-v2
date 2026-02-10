package com.tikam.simple_admin_v2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity(name = "refreshtoken")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;


//    public RefreshToken(User user, String token, Instant expiryDate) {
//        this.user = user;
//        this.token = token;
//        this.expiryDate = expiryDate;
//    }
}
