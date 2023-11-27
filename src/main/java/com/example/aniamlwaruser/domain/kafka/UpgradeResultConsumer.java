package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.request.UpgradeRequest;
import com.example.aniamlwaruser.service.INVTService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UpgradeResultConsumer {

    private final INVTService invtService;

    @KafkaListener(topics = "resultUpgrade", groupId = "Terrain")
    public void consume(UpgradeRequest result) throws IOException {
        invtService.updateUpgrade(result);
    }

}