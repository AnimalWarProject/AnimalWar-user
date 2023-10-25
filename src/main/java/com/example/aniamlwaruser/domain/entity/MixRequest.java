package com.example.aniamlwaruser.domain.entity;

import jakarta.persistence.*;

import java.util.UUID;

public class MixRequest {
    private Long id;
    private UUID userUUID;
    private String userId;
    private String nickName;
    private EntityType entityType;
    private String name;
    private Grade grade;

    public Mix toEntity() {
        return Mix.builder()
                .id(id)
                .userUUID(userUUID)
                .userId(userId)
                .nickName(nickName)
                .entityType(entityType)
                .name(name)
                .grade(grade)
                .build();
    }
}
