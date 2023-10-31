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

    private String attackTypeSkill;
    private String defenseTypeSkill;
    private String utilityTypeSkill;

    private String profileImage;

    @OneToMany(mappedBy = "user")
    private List<UserAnimal> animalInventory;

    @OneToMany(mappedBy = "user")
    private List<UserBuilding> buildingInventory;

    @Enumerated(EnumType.STRING)
    private Species species;

    private int freeTerrainNum;

    private LandForm landForm;


    public void exchangeGold(int amount) {
        this.food = food - 2000 * amount;
        this.wood = wood - 2000 * amount;
        this.iron = iron - 2000 * amount;
        this.gold = gold + 1000 * amount;
    }

    public void minusGold ( int amount){
        this.gold = gold - amount;
    }

    public void minusFreeTerrainNum () {
        this.freeTerrainNum = freeTerrainNum - 1;
    }

    public void resetFreeTerrainNum () {
        this.freeTerrainNum = 3;
    }

    public void updateLandForm (LandForm newLandForm){
        this.landForm = newLandForm;
    }

    public void addFood ( int amount){
        this.food += amount;
    }

    public void addIron ( int amount){
        this.iron += amount;
    }

    public void addWood ( int amount){
        this.wood += amount;
    }

    public void addGold ( int amount){
        this.gold += amount;

    }

    public void updateId(String id) {
        this.id = id;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}