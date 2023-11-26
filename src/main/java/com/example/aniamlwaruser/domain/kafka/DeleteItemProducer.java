package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.request.CancelBtnRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteItemProducer {
    private final KafkaTemplate<String, CancelBtnRequest> template;

    public void send(CancelBtnRequest request){
        template.send(TopicConfig.cancelMarketItem, request);
    }
}
