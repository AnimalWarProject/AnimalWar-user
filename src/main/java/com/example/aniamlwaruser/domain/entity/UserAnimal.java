package com.example.aniamlwaruser.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user_animals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAnimal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Animal animal;

    private int ownedQuantity;
    private int placedQuantity;
    private int upgrade;
}
