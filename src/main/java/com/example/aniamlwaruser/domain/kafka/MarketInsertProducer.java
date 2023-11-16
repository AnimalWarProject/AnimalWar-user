package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.request.MarketInsertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketInsertProducer {
    private final KafkaTemplate<String, MarketInsertRequest> template;

    public void send(MarketInsertRequest request){
        template.send(TopicConfig.insertMarket, request);
    }
}