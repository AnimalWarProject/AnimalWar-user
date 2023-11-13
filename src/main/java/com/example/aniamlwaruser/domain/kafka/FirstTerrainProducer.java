package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.dto.TerrainRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FirstTerrainProducer {
    private final KafkaTemplate<String, TerrainRequestDto> kafkaTemplate;

    public void sendCreateTerrainRequest(TerrainRequestDto terrainRequestDto) {
        kafkaTemplate.send(TopicConfig.firstTerrain, terrainRequestDto.getUserUUID().toString(), terrainRequestDto);
    }
}