package com.example.aniamlwaruser.domain.request;

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
}
