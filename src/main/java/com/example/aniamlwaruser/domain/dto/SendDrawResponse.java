package com.example.aniamlwaruser.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@Getter
@AllArgsConstructor
public class SendDrawResponse {
    private String name;
    private UUID userUUID;

    public SendDrawResponse() {
        // 기본 생성자
    }
}
