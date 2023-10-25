package com.example.aniamlwaruser.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mix")
public class Mix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID userUUID;
    private String userId;
    private String nickName;
    @Enumerated(EnumType.STRING)
    private EntityType entityType;
    private String name;
    @Enumerated(EnumType.STRING)
    private Grade grade;
}
