package com.example.aniamlwaruser.domain.entity;


import com.example.aniamlwaruser.domain.dto.OwnedAnimal;
import com.example.aniamlwaruser.domain.dto.OwnedBuilding;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.List;
import java.util.UUID;

@Table(name = "users")
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
@Configurable
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
    private int healthPower;
    private int battlePoint;

    private String profileImage;


    @OneToMany(mappedBy = "user")
    private List<OwnedAnimal> ownedAnimalList;

    @OneToMany(mappedBy = "user")
    private List<OwnedBuilding> ownedBuildingList;

    @Enumerated(EnumType.STRING)
    private Species species;

    private LandForm landForm;
}
