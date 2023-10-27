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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name ="userUUID")
    private User user;

    @ManyToOne
    @JoinColumn(name ="ainmalId")
    private Animal animal;

    private int ownedQuantity;
    private int placedQuantity;
    private int upgrade;

    public void insertDraw(Animal animal, Integer cnt) {
        this.animal = animal;
        this.ownedQuantity=cnt;
    }

}
