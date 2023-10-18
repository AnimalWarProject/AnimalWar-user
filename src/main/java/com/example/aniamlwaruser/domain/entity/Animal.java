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
    private Long id;
    private String name;
    private Grade grade;
    private Integer attackPower;
    private Integer defencePower;
    private Integer life;
    private Species species;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userUUID")
    private User user;
}