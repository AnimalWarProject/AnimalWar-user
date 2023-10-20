package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenerateTerrainProducer {
    private final KafkaTemplate<String, UUID> kafkaTemplate;

    public void requestTerrain(UUID userUUID) {
        kafkaTemplate.send("user-terrain-request-topic", userUUID);
    }
}
