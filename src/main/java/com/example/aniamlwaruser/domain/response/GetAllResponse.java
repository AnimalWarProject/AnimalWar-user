package com.example.aniamlwaruser.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAllResponse {

    private String name;
    private Integer ownedQuantity;
    private Integer upgrade;
    private Long animalId;
//    private String image; todo : 이미지 파일

}
