package com.example.aniamlwaruser.domain.request;

import com.example.aniamlwaruser.domain.entity.EntityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public class UpdateItem {
        private Long itemId;
        private int placedQuantity;
        private EntityType entityType;
    }
