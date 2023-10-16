package com.example.aniamlwaruser.config;


import com.example.aniamlwaruser.domain.entity.Species;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Builder @Getter
public class TokenInfo {
    private UUID userUUID;
    private String id;
    private String nickName;

}
