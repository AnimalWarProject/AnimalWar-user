package com.example.aniamlwaruser.domain.response;

import com.example.aniamlwaruser.domain.entity.Animal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnimalsResponse {

    private Long id;
    private Animal animal;
    private int ownedQuantity;
    private int placedQuantity;
    private int upgrade;

}
