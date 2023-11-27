package com.example.aniamlwaruser.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@Getter
@AllArgsConstructor
public class DrawResponse {
    private String name;
    private UUID userUUID;

    public DrawResponse() {
    }
}
