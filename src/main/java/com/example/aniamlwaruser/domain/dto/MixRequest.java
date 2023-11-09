package com.example.aniamlwaruser.domain.dto;


import com.example.aniamlwaruser.domain.entity.EntityType;
import com.example.aniamlwaruser.domain.entity.Grade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MixRequest {
    private Long id;
    private UUID userUUID;
    private String userId;
    private String nickName;
    private EntityType entityType;
    private Long animalId;
    private String name;
    private Grade grade;
    private List<Long> userAnimalList;
}