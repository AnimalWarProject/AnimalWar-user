package com.example.aniamlwaruser.domain.request;

import com.example.aniamlwaruser.domain.entity.Animal;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.entity.UserAnimal;

import java.util.UUID;

public record INVTRequest(
        //유저를 찾아야 하기위함
        UUID uuid,
        //어떤 동물, 어떤 건물을 배치한지 알아야 하기위함
        Long id,
        //내가 소지한 수량
        int ownedQuantity,
        //내가 배치한 수량
        int placedQuantity,
        //강화 수치
        int upgrade
) {
    public UserAnimal toEntity(){
        return UserAnimal.builder()
                .user(User.builder().userUUID(uuid).build())
                .animal(Animal.builder().animalId(id).build())
                .ownedQuantity(ownedQuantity)
                .placedQuantity(placedQuantity)
                .upgrade(upgrade)
                .build();
    }
}
