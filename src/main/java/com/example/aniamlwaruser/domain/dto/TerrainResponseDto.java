package com.example.aniamlwaruser.domain.dto;

import com.example.aniamlwaruser.domain.entity.LandForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerrainResponseDto {
    private UUID userUUID;
    private LandForm landForm;
    private int sea;
    private int land;
    private int mountain;
}
