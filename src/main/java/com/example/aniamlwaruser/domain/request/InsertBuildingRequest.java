package com.example.aniamlwaruser.domain.request;

import com.example.aniamlwaruser.domain.entity.Building;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.entity.UserBuilding;

import java.util.UUID;

public record InsertBuildingRequest(
        UUID userUUID,
        Long itemId,
        int ownedQuantity,
        int placedQuantity
) {

        public UserBuilding toEntity(){
                return UserBuilding
                        .builder()
                        .user(User.builder().userUUID(userUUID).build())
                        .building(Building.builder().buildingId(itemId).build())
                        .ownedQuantity(ownedQuantity)
                        .placedQuantity(placedQuantity)
                        .build();
        }

}
