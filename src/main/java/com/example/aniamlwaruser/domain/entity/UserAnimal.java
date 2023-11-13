package com.example.aniamlwaruser.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Table(name="user_animals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAnimal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="userUUID")
    private User user;

    @ManyToOne
    @JoinColumn(name ="animalId")
    private Animal animal;

    private int ownedQuantity;
    private int placedQuantity;
    private int upgrade;

}
