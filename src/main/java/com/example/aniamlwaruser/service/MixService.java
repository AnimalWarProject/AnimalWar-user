package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.config.TokenInfo;
import com.example.aniamlwaruser.controller.AuthController;
import com.example.aniamlwaruser.domain.entity.Animal;
import com.example.aniamlwaruser.domain.entity.Mix;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.entity.UserAnimal;
import com.example.aniamlwaruser.repository.AnimalRepository;
import com.example.aniamlwaruser.repository.UserAnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.*;

@Service
@RequiredArgsConstructor
public class MixService {
    private final AnimalRepository animalRepository;
    private final UserAnimalRepository userAnimalRepository;

//    #4 유저 인벤토리에 넣기
//    1. 믹스된걸 받은 것을 참고해서 도감에서 해당하는 걸 찾아
//    2. 찾은걸 인벤토리에(UserAnimal) 넣어
    public void saveInventory(Mix mix) {
//        합성 완료한 동물 도감에서 찾기
        Optional<Animal> animalById = animalRepository.findById(mix.getAnimalId());

//        인벤토리에 있는 동물 확인
        Optional<UserAnimal> currentAnimalById = userAnimalRepository.findByAnimal_AnimalId(mix.getAnimalId());

        if(currentAnimalById.isPresent()){
            UserAnimal userAnimal = UserAnimal.builder()
                    .id(currentAnimalById.get().getId())
                    .user(currentAnimalById.get().getUser())
                    .animal(animalById.get())  // 합성 완료한 동물
                    .ownedQuantity(currentAnimalById.get().getOwnedQuantity() +1) // 보유 개수
                    .batchedQuantity(currentAnimalById.get().getBatchedQuantity()) // 배치 개수
                    .upgrade(0) // 새로 받은 캐릭의 강화단계는 디폴트 0
                    .build();
            userAnimalRepository.save(userAnimal);
        } else {
            throw new IllegalArgumentException("해당 동물을 찾을 수 없습니다.");
        }
    }



//    #5 유저 인벤토리에서 믹스에 사용한 4개를 삭제


}
