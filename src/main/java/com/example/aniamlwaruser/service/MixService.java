package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.domain.entity.Animal;
import com.example.aniamlwaruser.domain.entity.Mix;
import com.example.aniamlwaruser.domain.entity.UserAnimal;
import com.example.aniamlwaruser.domain.request.MixRequest;
import com.example.aniamlwaruser.repository.AnimalRepository;
import com.example.aniamlwaruser.repository.UserAnimalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MixService {
    private final AnimalRepository animalRepository;
    private final UserAnimalRepository userAnimalRepository;

//    TODO 합성 실패/성공했을 때 메시지 띄우기(오류처리)..
    @Transactional //saveInventory와 deleteMixed 하나라도 실패하면 둘 다 하면 안됨..
    public void saveInventoryAndDeleteMixed(MixRequest mixRequest) {
        try {
            // saveInventory 실행
            saveInventory(mixRequest);

            // deleteMixed 실행
            userAnimalRepository.deleteAllByAnimal_AnimalIdIn(mixRequest.getUserAnimalList());
//            deleteMixed(mixRequest.getUserAnimalList());
        } catch (Exception e) {
            System.out.println("합성에 실패했습니다. 관리자에게 문의해주세요.");
        }
    }



//    #4 유저 인벤토리에 넣기
//    1. 믹스된걸 받은 것을 참고해서 도감에서 해당하는 걸 찾아
//    2. 찾은걸 인벤토리에(UserAnimal) 넣어
    public void saveInventory(MixRequest mixRequest) {
//        합성 완료한 동물 도감에서 찾기
        Optional<Animal> animalById = animalRepository.findById(mixRequest.getAnimalId());

//        인벤토리에 있는 동물 확인
        Optional<UserAnimal> currentAnimalById = userAnimalRepository.findByAnimal_AnimalId(mixRequest.getAnimalId());

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
//    public void deleteMixed(List<Long> selectedUserAnimalList) {
//        userAnimalRepository.deleteAllById(selectedUserAnimalList);
//    }
}
