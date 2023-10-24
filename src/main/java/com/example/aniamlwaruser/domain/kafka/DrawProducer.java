package com.example.aniamlwaruser.domain.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrawProducer {

    private final KafkaTemplate<String, Integer> kafkaTemplate;

    public void requestDraw(Integer count) {
        kafkaTemplate.send("drawCount", count);
    }
}
