package com.example.aniamlwaruser.domain.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateTerrainProducer {
    private final KafkaTemplate<String, UUID> kafkaTemplate;

    public void updateTerrain(UUID userUUID) {
        kafkaTemplate.send("user-terrain-request-topic", userUUID);
    }
}
