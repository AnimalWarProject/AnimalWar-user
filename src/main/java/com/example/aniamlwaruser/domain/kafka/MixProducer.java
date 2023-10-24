package com.example.aniamlwaruser.domain.kafka;

import com.example.aniamlwaruser.domain.entity.Animal;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AnimalMixProducer {
    // 4. 이곳에서 보내는 카프카 템플릿을 정의한다.
    private final KafkaTemplate<String, List<Animal>> kafkaTemplate;

    // 5. 내가 보내고 싶은 topic 으로 보낸다.
    public void send(List<Animal> animal) {
        // 6. 카프카로 해당하는 주제로 data(animal)를 보낸다
        CompletableFuture<SendResult<String, List<Animal>>> send = kafkaTemplate.send(TopicConfig.animalMix, animal);

        send.thenAccept(x -> {
            System.out.println("전송다됨");
        });
    }

    // 6. 그럼 이제 카프카로 보내는 기능은 만들어졌으니 그걸 어디에 쓸까 ? 이건 내 로직에 따라 다름.
    // 내가 카프카로 보내는 걸 써야할 로직은  AnimalService 여기에 있는 것 같으니 여기로 가보자.

}