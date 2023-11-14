package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.dto.TerrainRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateTerrainProducer {
    private final KafkaTemplate<String, TerrainRequestDto> kafkaTemplate;

    public void updateTerrain(TerrainRequestDto terrainRequestDto) {
        kafkaTemplate.send("user-terrain-request-topic", terrainRequestDto);
    }
}
