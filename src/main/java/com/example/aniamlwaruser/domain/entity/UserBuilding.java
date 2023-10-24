package com.example.aniamlwaruser.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Table(name="user_buildings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserBuilding {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="userUUID")
    private User user;


    @ManyToOne
    @JoinColumn(name="buildingId")
    private Building building;

    private int ownedQuantity;
    private int placedQuantity;
    private int upgrade;
}
