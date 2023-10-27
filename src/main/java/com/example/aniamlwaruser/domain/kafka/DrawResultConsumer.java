package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.dto.SendDrawResponse;
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

    @KafkaListener(topics = "resultDraw", groupId = "Terrain")
    public void consume(List<SendDrawResponse> result) throws IOException {
        System.out.println("consumer : "+result);
        userService.insertDrawResponse(result);

    }

}