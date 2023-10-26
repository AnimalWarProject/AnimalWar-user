package com.example.aniamlwaruser.domain.entity;

import java.util.UUID;

public class MixRequest {
    private Long id;
    private UUID userUUID;
    private String userId;
    private String nickName;
    private EntityType entityType;
    private Long animalId;
    private String name;
    private Grade grade;

    public Mix toEntity() {
        return Mix.builder()
                .id(id)
                .userUUID(userUUID)
                .userId(userId)
                .nickName(nickName)
                .entityType(entityType)
                .animalId(animalId)
                .name(name)
                .grade(grade)
                .build();
    }
}
