package com.example.aniamlwaruser.domain.dto;


import com.example.aniamlwaruser.domain.entity.EntityType;
import com.example.aniamlwaruser.domain.entity.Grade;
import com.example.aniamlwaruser.domain.entity.Species;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MixRequest {
    private UUID userUUID;

    private EntityType entityType;
    private Long mixResultId;
    private String name;
    private Grade grade;
    private Species species;
    private String imagePath;
    private List<Long> selectedList;

}