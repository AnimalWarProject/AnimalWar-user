package com.example.aniamlwaruser.domain.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class TopicConfig {
    //    public final static String customerTopic = "customer";
    public final static String matchTopic = "match";
    public final static String mixResult = "mix";
    public final static String firstTerrain = "first-terrain-request-topic";
    public final static String updateTerrain = "user-terrain-request-topic";
    public final static String insertMarketAnimal = "market-animal";
    public final static String insertMarketBuilding = "market-building";
    public final static String buyMarketAnimal = "market-buy-item";
    public final static String cancelMarketItem = "market-cancel-item";

    @Bean
    public NewTopic ownerTopic(){
        return TopicBuilder
                .name(matchTopic)
                .replicas(1)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic mixResultTopic(){
        return TopicBuilder
                .name(mixResult)
                .replicas(1)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic firstTerrainTopic(){
        return TopicBuilder
                .name(firstTerrain)
                .replicas(1)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic updateTerrainTopic(){
        return TopicBuilder
                .name(updateTerrain)
                .replicas(1)
                .partitions(1)
                .build();
    }
}