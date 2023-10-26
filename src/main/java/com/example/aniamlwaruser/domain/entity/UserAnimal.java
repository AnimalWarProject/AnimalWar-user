package com.example.aniamlwaruser.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="user_animals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAnimal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
