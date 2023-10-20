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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    //이슈테스트
    @ManyToOne(fetch = FetchType.LAZY)
    private Building building;
    private int ownedQuantity;
    private int placedQuantity;
    private int upgrade;
}
