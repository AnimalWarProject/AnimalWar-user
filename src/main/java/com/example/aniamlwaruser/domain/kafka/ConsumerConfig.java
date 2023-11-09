package com.example.aniamlwaruser.domain.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

// consumer 쪽의 환경설정
@Configuration
public class ConsumerConfig {

    // 받을때 JSON 받겠다.
    @Bean
    public RecordMessageConverter converter() {
        return new JsonMessageConverter();
    }


    // 만약 data가 넘어오는 도중에 에러가 낫다면 1초에 2번씩 더 요청할 것임.
    @Bean
    public CommonErrorHandler errorHandler(KafkaOperations<Object, Object> kafkaOperations) {
        return new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(kafkaOperations),
                new FixedBackOff(1000L, 2)
        );
    }
}