package com.example.aniamlwaruser.domain.request;

import com.example.aniamlwaruser.domain.entity.UserAnimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalINBTRequest {

    //유저를 찾아야 하기위함
    private UUID uuid;
    //어떤 동물을 배치한지 알아야 하기위함
    private Long animalID;
    //내가 소지한 수량
    private int ownedQuantity;
    //내가 배치한 수량
    private int placedQuantity;
    //강화 수치
    private int upgrade;

}
