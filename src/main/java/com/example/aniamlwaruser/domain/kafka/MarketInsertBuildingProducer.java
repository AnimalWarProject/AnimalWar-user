package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.request.MarketBuildingInsertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketInsertBuildingProducer {
    private final KafkaTemplate<String, MarketBuildingInsertRequest> template;

    public void send(MarketBuildingInsertRequest request){
        template.send(TopicConfig.insertMarketBuilding, request);
    }

}