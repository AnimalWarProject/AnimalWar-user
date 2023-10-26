package com.example.aniamlwaruser.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Table(name = "users")
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userUUID;

    private String id;
    private String password;
    private String nickName;

    private int food;
    private int iron;
    private int wood;
    private int gold;

    private int attackPower;
    private int defensePower;
    private int life;
    private int battlePoint;

    private String profileImage;

//    @OneToMany(mappedBy = "user")
//    private List<UserAnimal> animalInventory;
//
//    @OneToMany(mappedBy = "user")
//    private List<UserBuilding> buildingInventory;

    @Enumerated(EnumType.STRING)
    private Species species;

    private int FreeTerrainNum;

    private LandForm landForm;

    public void exchangeGold(int amount){
        this.food = food-2000*amount;
        this.wood = wood-2000*amount;
        this.iron = iron-2000*amount;
        this.gold = gold+1000*amount;
    }
}
