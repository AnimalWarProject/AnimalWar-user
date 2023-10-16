package com.example.aniamlwaruser.domain.entity;

import com.example.aniamlwaruser.client.api.DrawClient;
import com.example.aniamlwaruser.domain.dto.AnimalDto;
import com.example.aniamlwaruser.domain.dto.BuildingDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.ArrayList;
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
    private int battlePoint;

    private String profileImage;

    @ElementCollection
    @CollectionTable(name = "user_animals", joinColumns = @JoinColumn(name = "user_uuid"))
    private List<Long> ownedAnimalIds = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "user_buildings", joinColumns = @JoinColumn(name = "user_uuid"))
    private List<Long> ownedBuildingIds = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Species species;

    private LandForm landForm;

}
