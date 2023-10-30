package com.example.aniamlwaruser.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @Builder @Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long animalId;
    private String name;
    private Grade grade;
    private Integer attackPower;
    private Integer defencePower;
    private Integer life;
    private Species species;

//    @OneToMany(mappedBy = "animal")
//    private List<UserAnimal> userAnimals;
}