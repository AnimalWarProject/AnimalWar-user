package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.request.BuyBtnRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyItemProducer {
    private final KafkaTemplate<String, BuyBtnRequest> template;

    public void send(BuyBtnRequest request){
        template.send(TopicConfig.buyMarketAnimal, request);
    }

}