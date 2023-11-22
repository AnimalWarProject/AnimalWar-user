package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.dto.MixRequest;
import com.example.aniamlwaruser.domain.entity.EntityType;
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
        System.out.println("consumer-------------------------"+mixRequest.getMixResultId());
        System.out.println("consumer-------------------------"+mixRequest.getName());
        System.out.println("consumer-------------------------"+mixRequest.getGrade());
        System.out.println("consumer-------------------------"+mixRequest.getEntityType());
        System.out.println("consumer-------------------------"+mixRequest.getImagePath());
        System.out.println("consumer-------------------------"+mixRequest.getSelectedList());

        userService.saveInventoryAndDeleteMixed(mixRequest);


    }
}