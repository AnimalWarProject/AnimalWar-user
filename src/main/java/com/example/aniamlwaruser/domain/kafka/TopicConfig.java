package com.example.aniamlwaruser.domain.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class TopicConfig {
//    public final static String customerTopic = "customer";
    public final static String matchTopic = "match";

//    @Bean
//    public NewTopic customerTopic(){
//        return new NewTopic(customerTopic, 1, (short) 1);
//    }
    @Bean
    public NewTopic ownerTopic(){
        return TopicBuilder
                .name(matchTopic)
                .replicas(1)
                .partitions(1)
                .build();
    }
}
