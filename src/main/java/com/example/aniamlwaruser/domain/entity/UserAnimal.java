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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name ="userUUID")
    private User user;

    @ManyToOne
    @JoinColumn(name ="ainmalId")
    private Animal animal;

    private int ownedQuantity;
    private int batchedQuantity;
    private int upgrade;
}
