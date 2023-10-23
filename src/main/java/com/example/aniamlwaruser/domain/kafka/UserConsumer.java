package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.dto.TerrainRequestDto;
import com.example.aniamlwaruser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConsumer {

    private final UserService userService;

    @KafkaListener(topics = "terrain-response-topic", groupId = "user-info-updater")
    public void updateUserInfo(TerrainRequestDto terrainRequestDto) {
        userService.updateUserTerrain(terrainRequestDto);
    }
}
