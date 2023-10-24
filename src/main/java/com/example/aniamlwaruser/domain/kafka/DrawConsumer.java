package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.response.DrawResultResponseDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DrawConsumer {
    private List<DrawResultResponseDto> receivedData;

    @KafkaListener(topics = "resultDraw", groupId = "Terrain")
    public void consume(List<DrawResultResponseDto> result) throws IOException {
        this.receivedData = result;
        System.out.println(result);
    }

    public List<DrawResultResponseDto> getReceivedData() {
        return receivedData;
    }
}