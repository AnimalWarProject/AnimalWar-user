package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.request.UpgradeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpgradeProducer {
    private final KafkaTemplate<String, UpgradeRequest> template;

    public void send(UpgradeRequest request){
        System.out.println("upgrade : " + request.userUUID() + ", " + request.itemId() + ", " + request.buff());
        template.send(TopicConfig.upgrade, request);
    }

}