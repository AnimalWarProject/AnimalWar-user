package com.example.aniamlwaruser.domain.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyItemRequest {

    private UUID userId;
    private Long itemId;
    private String name;
    private String grade;
    private String type;
    private Integer buff;
    private Integer price;

}
