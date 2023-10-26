package com.example.aniamlwaruser.domain.request;

import com.example.aniamlwaruser.domain.entity.*;
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
