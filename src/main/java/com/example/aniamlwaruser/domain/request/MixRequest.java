package com.example.aniamlwaruser.domain.request;

import com.example.aniamlwaruser.domain.entity.EntityType;
import com.example.aniamlwaruser.domain.entity.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    public MixRequest toEntity() {
        return MixRequest.builder()
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
