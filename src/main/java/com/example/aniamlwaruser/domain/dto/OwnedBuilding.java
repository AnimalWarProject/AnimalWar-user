package com.example.aniamlwaruser.domain.dto;

import com.example.aniamlwaruser.domain.entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "owned_buildings")
@Data
public class OwnedBuilding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ownedBuildingId;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userUUID")
    private User user;
}
