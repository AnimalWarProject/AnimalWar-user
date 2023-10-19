package com.example.aniamlwaruser.kafka;

import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProducer {
    private final KafkaTemplate<String, UserResponse> template;

    public void send(UserResponse request){
        template.send(TopicConfig.userTopic, request);
    }
}
