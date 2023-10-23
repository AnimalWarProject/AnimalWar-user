package com.example.aniamlwaruser.domain.dto;

import com.example.aniamlwaruser.domain.entity.LandForm;
import lombok.Data;

import java.util.UUID;


@Data
public class TerrainRequestDto {
    private UUID userUUID;
    private LandForm landForm;
}
