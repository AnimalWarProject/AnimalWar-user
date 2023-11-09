package com.example.aniamlwaruser.domain.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class SendResultUpgrade {
    private UUID userUUID;
    private Long animalId;
    private Integer resultUpgrade;

    public SendResultUpgrade() {
    }
}
