package com.example.aniamlwaruser.domain.request;

import java.util.UUID;

public record MarketInsertRequest(
        UUID userUUID,
        Long itemId,
        String name,
        String grade,
        String species,
        Integer buff,
        Integer price

) {

}
