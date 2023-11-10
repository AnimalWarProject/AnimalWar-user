package com.example.aniamlwaruser.domain.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TopicConfig {
    // 보내는 사람(Producer) 기준

    // 1. topic 의 이름을 정의한다.
    public final static String mixResult = "mixResult";

    // 2. 정의한 topic 의 이름으로 topic 을 생성한다.
    @Bean
    public NewTopic mixResultTopic() {
        return new NewTopic(mixResult, 1, (short) 1);
    }

    // 3. topic 을 생성 했으니.. 이제 그 topic으로 data를 보내보자.. AnimalMixProducer.java 로 가보자..
}
