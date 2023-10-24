package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.kafka.TopicConfig;
import com.example.aniamlwaruser.domain.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchProducer {
    private final KafkaTemplate<String, UserResponse> template;

    public void send(UserResponse request){
        template.send(TopicConfig.matchTopic, request);
    }
}