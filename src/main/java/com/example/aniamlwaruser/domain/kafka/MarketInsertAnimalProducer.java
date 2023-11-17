package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.request.MarketAnimalInsertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketInsertAnimalProducer {
    private final KafkaTemplate<String, MarketAnimalInsertRequest> template;

    public void send(MarketAnimalInsertRequest request){
        template.send(TopicConfig.insertMarketAnimal, request);
    }

}