package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.dto.MixRequest;
import com.example.aniamlwaruser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MixConsumer {
    private final UserService userService;

    @KafkaListener(topics = TopicConfig.mixResult, groupId = "animalwar-user-group")
    public void consumeMixResult(MixRequest mixRequest) {
        userService.saveInventoryAndDeleteMixed(mixRequest);
    }
}