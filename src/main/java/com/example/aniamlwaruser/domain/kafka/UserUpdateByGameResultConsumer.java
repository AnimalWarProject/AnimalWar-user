package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.dto.UserUpdateByGameResultDto;
import com.example.aniamlwaruser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.example.aniamlwaruser.domain.kafka.TopicConfig.GAME_RESULT_TOPIC;

@Service
@RequiredArgsConstructor
public class UserUpdateByGameResultConsumer {
    private final UserService userService;
    @KafkaListener(topics = GAME_RESULT_TOPIC)
    public void UserUpdateByGameResultListener(UserUpdateByGameResultDto result){
        userService.userUpdateByGameResult(result);
    }
}