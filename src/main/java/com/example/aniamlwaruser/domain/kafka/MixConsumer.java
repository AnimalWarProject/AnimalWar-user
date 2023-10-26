package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.entity.Mix;
import com.example.aniamlwaruser.domain.entity.MixRequest;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.entity.UserAnimal;
import com.example.aniamlwaruser.repository.MixRepository;
import com.example.aniamlwaruser.repository.UserAnimalRepository;
import com.example.aniamlwaruser.service.MixService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MixConsumer {
    private final UserAnimalRepository userAnimalRepository;
    private final MixRepository mixRepository;
    private final MixService mixService;

// 4. 해당 주제를 구독하는 처리를 먼저 해준다.
    @KafkaListener(topics = TopicConfig.mixResult)
    public void mixResultListen(Mix mix) {
        mixRepository.save(mix);
        mixService.saveInventory(mix);
    }

}
