package com.example.aniamlwaruser.domain.request;

import com.example.aniamlwaruser.domain.entity.Animal;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.entity.UserAnimal;

import java.util.UUID;

public record InsertAnimalRequest(
        UUID userUUID,
        Long itemId,
        int ownedQuantity,
        int placedQuantity,
        int upgrade
        ) {

        public UserAnimal toEntity(){
                return UserAnimal
                        .builder()
                        .user(User.builder().userUUID(userUUID).build())
                        .animal(Animal.builder().animalId(itemId).build())
                        .ownedQuantity(ownedQuantity)
                        .placedQuantity(placedQuantity)
                        .upgrade(upgrade)
                        .build();
        }

}
