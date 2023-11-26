package com.example.aniamlwaruser.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoldPointUpdateDto {
    private int battlePoint;
    private int gold;
}
