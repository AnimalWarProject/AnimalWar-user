package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.request.ExchangeRequest;
import com.example.aniamlwaruser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<String> exchangeGold(UUID uuid){

        User user = userRepository.findByUserUUID(uuid).get();

        if(user.getWood()<2000||user.getIron()<2000||user.getFood()<2000){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No ingredient");
        }
        user.exchangeGold();

        return ResponseEntity.ok("success");
    }
}
