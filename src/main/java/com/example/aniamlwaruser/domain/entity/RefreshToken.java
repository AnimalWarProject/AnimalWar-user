package com.example.aniamlwaruser.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    @Column(name = "user_id")
    private String id;

    private String token;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
}
