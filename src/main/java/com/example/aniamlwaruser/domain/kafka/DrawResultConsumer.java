package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.dto.DrawResponse;
import com.example.aniamlwaruser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrawResultConsumer {

    private final UserService userService;

    @KafkaListener(topics = "resultAnimalDraw", groupId = "Terrain")
    public void animalConsume(List<DrawResponse> result) throws IOException {
        System.out.println("동물 consumer : "+result);
        userService.insertAnimalDrawResponse(result);

    }

    @KafkaListener(topics = "resultBuildingDraw", groupId = "Terrain")
    public void buildingConsume(List<DrawResponse> result) throws IOException {
        System.out.println("건물 consumer : "+result);
        userService.insertBuildingDrawResponse(result);

    }

}