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
    private int maxLife;
    private int battlePoint;

    private int totalFoodRate;
    private int totalWoodRate;
    private int totalIronRate;

    private String profileImage;

    @OneToMany(mappedBy = "user")
    private List<UserAnimal> animalInventory;

    @OneToMany(mappedBy = "user")
    private List<UserBuilding> buildingInventory;

    @Enumerated(EnumType.STRING)
    private Species species;

    private int freeTerrainNum;

    @Enumerated(EnumType.STRING)
    private LandForm landForm;

    private int sea;
    private int land;
    private int mountain;


    public void exchangeGold() {
        this.food = food - 2000 ;
        this.wood = wood - 2000 ;
        this.iron = iron - 2000 ;
        this.gold = gold + 1000 ;
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

    public void updateSea(int sea){
        this.sea = sea;
    }

    public void updateMountain(int mountain){
        this.mountain = mountain;
    }

    public void updateLand(int land){
        this.land = land;
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

    public void updateAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void updateDefensePower(int defensePower) {
        this.defensePower = defensePower;
    }

    public void updateLife(int life) {
        this.life = life;
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


    public void calculateTotalRates() {
        // 모든 건물의 rate를 0으로 초기화합니다.
        this.totalFoodRate = 0;
        this.totalWoodRate = 0;
        this.totalIronRate = 0;

        for (UserBuilding userBuilding : this.buildingInventory) {
            if (userBuilding.getPlacedQuantity() > 0) {
                Building building = userBuilding.getBuilding();
                this.totalFoodRate += building.getFoodRate() * userBuilding.getPlacedQuantity();
                this.totalWoodRate += building.getWoodRate() * userBuilding.getPlacedQuantity();
                this.totalIronRate += building.getIronRate() * userBuilding.getPlacedQuantity();
            }
        }
    }


    public void updateBattleStats() {
        this.attackPower =0;
        this.defensePower =0;
        this.life =0;
        for (UserBuilding userBuilding : this.buildingInventory) {
            if (userBuilding.getPlacedQuantity() > 0) {
                Building building = userBuilding.getBuilding();
                this.attackPower += building.getAttackPower() * userBuilding.getPlacedQuantity();
                this.defensePower += building.getDefencePower() * userBuilding.getPlacedQuantity();
                this.life += building.getLife() * userBuilding.getPlacedQuantity();
            }
        }

        for (UserAnimal userAnimal : this.animalInventory) {
            if (userAnimal.getPlacedQuantity() > 0) {
                Animal animal = userAnimal.getAnimal();
                this.attackPower += animal.getAttackPower() * userAnimal.getPlacedQuantity();
                this.defensePower += animal.getDefencePower() * userAnimal.getPlacedQuantity();
                this.life += animal.getLife() * userAnimal.getPlacedQuantity();
            }
        }
    }

}

