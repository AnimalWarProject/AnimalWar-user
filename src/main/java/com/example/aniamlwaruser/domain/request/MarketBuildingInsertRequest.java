package com.example.aniamlwaruser.domain.request;

import java.util.UUID;

public record MarketBuildingInsertRequest(
        UUID userUUID,
        Long itemId,
        String name,
        String grade,
        String species,
        Integer price,
        String imagePath

) {

}
