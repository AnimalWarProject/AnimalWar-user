package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.dto.TerrainRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProducer {
    private final KafkaTemplate<String, TerrainRequestDto> kafkaTemplate;

    public void requestTerrain(TerrainRequestDto terrainRequestDto) {
        kafkaTemplate.send("user-map-request-topic", terrainRequestDto);
    }
}
