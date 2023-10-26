package com.example.aniamlwaruser.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long animalId;
    private String name;
    @Enumerated(EnumType.STRING)
    private Grade grade;
    private Integer attackPower;
    private Integer defencePower;
    private Integer life;
    @Enumerated(EnumType.STRING)
    private Species species;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userUUID")
    private User user;
}