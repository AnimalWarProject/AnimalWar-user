package com.example.aniamlwaruser.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "buildings")
public class Building {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buildingId;
    private String name;
    @Enumerated(EnumType.STRING)
    private Grade grade;
    private Integer attackPower;
    private Integer defencePower;
    private Integer life;
    private Integer woodRate;
    private Integer ironRate;
    private Integer foodRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userUUID")
    private User user;
}