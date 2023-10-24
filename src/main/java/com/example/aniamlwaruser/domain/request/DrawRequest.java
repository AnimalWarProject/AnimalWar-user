package com.example.aniamlwaruser.domain.request;

import java.util.UUID;

public record DrawRequest(UUID userUUID, Integer count) {
}
