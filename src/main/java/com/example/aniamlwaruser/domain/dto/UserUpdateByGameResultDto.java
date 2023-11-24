package com.example.aniamlwaruser.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserUpdateByGameResultDto {
    private String attackerId;
    private int gold;
    private int battlePoint;

}

