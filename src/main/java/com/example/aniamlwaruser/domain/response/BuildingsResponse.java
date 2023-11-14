package com.example.aniamlwaruser.domain.response;

import com.example.aniamlwaruser.domain.entity.Animal;
import com.example.aniamlwaruser.domain.entity.Building;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BuildingsResponse {

    private Long id;
    private Building building;
    private int ownedQuantity;
    private int placedQuantity;
}
