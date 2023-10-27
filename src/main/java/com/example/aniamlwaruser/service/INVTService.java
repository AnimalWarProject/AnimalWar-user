package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.domain.dto.SendResultUpgrade;
import com.example.aniamlwaruser.domain.entity.*;
import com.example.aniamlwaruser.domain.request.INVTRequest;
import com.example.aniamlwaruser.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class INVTService {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final BuildingRepository buildingRepository;
    private final AnimalINVTRepository animalINVTRepository;
    private final BuildingINVTRepository buildingINVTRepository;


    public void insertAnimal(INVTRequest invtRequest){

        User user = userRepository.findByUserUUID(invtRequest.uuid())
                .orElseThrow(()->new RuntimeException("Not found User"));

        Animal animal = animalRepository.findById(invtRequest.id())
                .orElseThrow(() -> new RuntimeException("Not found animal"));

        UserAnimal build = UserAnimal.builder()
                .user(user)
                .animal(animal)
                .ownedQuantity(invtRequest.ownedQuantity())
                .placedQuantity(invtRequest.placedQuantity())
                .upgrade(invtRequest.upgrade())
                .build();


        animalINVTRepository.save(build);

    }

    public void insertBuilding(INVTRequest invtRequest){

        User user = userRepository.findByUserUUID(invtRequest.uuid())
                .orElseThrow(()->new RuntimeException("Not found User"));

        Building building = buildingRepository.findById(invtRequest.id())
                .orElseThrow(() -> new RuntimeException("Not found animal"));

        UserBuilding build = UserBuilding.builder()
                .user(user)
                .building(building)
                .ownedQuantity(invtRequest.ownedQuantity())
                .placedQuantity(invtRequest.placedQuantity())
                .upgrade(invtRequest.upgrade())
                .build();


        buildingINVTRepository.save(build);

    }

    @Transactional
    public void updateUpgrade(SendResultUpgrade result){
        Optional<UserAnimal> qtyFindByInven = animalINVTRepository.findByInven(result.getUserUUID(), result.getAnimalId());
        UserAnimal ua = qtyFindByInven.get();
        if (ua.getOwnedQuantity() < 2){
            animalINVTRepository.deleteFindByInven(result.getUserUUID(), result.getAnimalId()); // 남은 수량이 1개 미만이면 삭제
        }else {
            ua.setUpgrade(ua.getOwnedQuantity()-1);
        }
        INVTRequest invtRequest = new INVTRequest(result.getUserUUID(), result.getAnimalId(), 1, 0, result.getResultUpgrade()); // 강화된 동물을 저장한다.
        animalINVTRepository.save(invtRequest.toEntity());
    }

}
