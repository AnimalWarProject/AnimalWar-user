package com.example.aniamlwaruser.domain.request;

import java.util.UUID;

public record CancelBtnRequest(
        UUID userUUID,
        Long itemId
) {
}
