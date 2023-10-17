package com.example.aniamlwaruser.domain.dto;

import com.example.aniamlwaruser.domain.entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "owned_animals")
@Data
public class OwnedAnimal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ownedAnimalId;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userUUID")
    private User user;
}
