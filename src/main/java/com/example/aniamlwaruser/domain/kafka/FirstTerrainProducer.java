package com.example.aniamlwaruser.domain.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FirstTerrainProducer {
    private final KafkaTemplate<String, UUID> kafkaTemplate;

    public void sendCreateTerrainRequest(UUID userUUID) {
        kafkaTemplate.send("first-terrain-request-topic", userUUID.toString(), userUUID);
    }
}