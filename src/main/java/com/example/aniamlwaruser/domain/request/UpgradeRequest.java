package com.example.aniamlwaruser.domain.request;

import java.util.UUID;

public record UpgradeRequest(
        UUID userUUID,
        Long itemId,
        Integer buff
) {
}
