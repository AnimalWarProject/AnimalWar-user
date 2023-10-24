package com.example.aniamlwaruser.domain.request;

import java.util.UUID;

public record ExchangeRequest(

        UUID uuid,
        int amount
) {
}
