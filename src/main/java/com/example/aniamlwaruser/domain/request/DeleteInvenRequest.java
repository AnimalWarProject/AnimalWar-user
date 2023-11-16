package com.example.aniamlwaruser.domain.request;



public record DeleteInvenRequest(
        Long itemId,
        String name,
        String grade,
        String species,
        Integer buff,
        Integer price
) {
}
