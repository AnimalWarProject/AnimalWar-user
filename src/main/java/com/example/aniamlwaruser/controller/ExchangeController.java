package com.example.aniamlwaruser.controller;

import com.example.aniamlwaruser.config.TokenInfo;
import com.example.aniamlwaruser.domain.request.ExchangeRequest;
import com.example.aniamlwaruser.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping
    public ResponseEntity<String> exchangeGold(@AuthenticationPrincipal TokenInfo tokenInfo){

        return exchangeService.exchangeGold(tokenInfo.getUserUUID());
    }
}
