package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.request.ExchangeRequest;
import com.example.aniamlwaruser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final UserRepository userRepository;

    public void exchangeGold(ExchangeRequest exchangeRequest){

        User user = userRepository.findByUserUUID(exchangeRequest.uuid()).get();

//        user.setFood(user.getFood()-2000*exchangeRequest.amount());
//        user.setWood(user.getWood()-2000*exchangeRequest.amount());
//        user.setIron(user.getIron()-2000*exchangeRequest.amount());
//        user.setGold(user.getGold()+1000*exchangeRequest.amount());

        user.exchangeGold(exchangeRequest.amount());
    }
}
