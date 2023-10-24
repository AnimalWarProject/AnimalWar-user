package com.example.aniamlwaruser.controller;

import com.example.aniamlwaruser.domain.request.ExchangeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/exchange")
public class ExchangeController {

    @PostMapping
    public void exchangeGold(@RequestBody ExchangeRequest request){

    }
}
