package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.domain.entity.*;
import com.example.aniamlwaruser.domain.request.INVTRequest;
import com.example.aniamlwaruser.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
