package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.domain.entity.*;
import com.example.aniamlwaruser.domain.kafka.MarketInsertAnimalProducer;
import com.example.aniamlwaruser.domain.kafka.MarketInsertBuildingProducer;
import com.example.aniamlwaruser.domain.request.*;
import com.example.aniamlwaruser.domain.response.AnimalsResponse;
import com.example.aniamlwaruser.domain.response.BuildingsResponse;
import com.example.aniamlwaruser.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class INVTService {

    private final MarketInsertAnimalProducer marketInsertAnimalProducer;
    private final MarketInsertBuildingProducer marketInsertBuildingProducer;

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final BuildingRepository buildingRepository;
    private final AnimalINVTRepository animalINVTRepository;
    private final BuildingINVTRepository buildingINVTRepository;


    public List<AnimalsResponse> getAnimals(UUID userUUID){
        List<UserAnimal> byUserUUID = animalINVTRepository.findUserAnimalByUserUUID(userUUID);
        return byUserUUID.stream().map((e) -> new AnimalsResponse(
                e.getId(),
                e.getAnimal(),
                e.getOwnedQuantity(),
                e.getPlacedQuantity(),
                e.getUpgrade()
        )).toList();
    }

    public List<BuildingsResponse> getBuildings(UUID userUUID){
        List<UserBuilding> byUserUUID = buildingINVTRepository.findUserBuildingByUserUUID(userUUID);
        return byUserUUID.stream().map((e) -> new BuildingsResponse(
                e.getId(),
                e.getBuilding(),
                e.getOwnedQuantity(),
                e.getPlacedQuantity()
        )).toList();
    }

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
                .build();


        buildingINVTRepository.save(build);

    }

    @Transactional
    public Boolean deleteInvenAnimal(UUID userUUID, DeleteAnimalRequest request){
        Optional<UserAnimal> byUserAndAnimalId = animalINVTRepository.findByUserAndAnimalId(userUUID, request.getItemId());

        if (byUserAndAnimalId.isPresent()){
            if (byUserAndAnimalId.get().getOwnedQuantity() < 2){ // 만약 1개이하라면 삭제
                UserAnimal userAnimal = byUserAndAnimalId.get();
                animalINVTRepository.delete(userAnimal);
            }else {
                UserAnimal userAnimal = byUserAndAnimalId.get(); // 2개이상이면 수량 -1
                userAnimal.setOwnedQuantity(userAnimal.getOwnedQuantity()-1);
            }
        }else{
            System.out.println("잘못된 정보입니다.");
        }
        MarketAnimalInsertRequest marketAnimalInsertRequest = new MarketAnimalInsertRequest(
                userUUID,
                request.getItemId(),
                request.getName(),
                request.getGrade(),
                request.getSpecies(),
                request.getBuff(),
                request.getPrice(),
                request.getImagePath());
        marketInsertAnimalProducer.send(marketAnimalInsertRequest);
        return true;
    }

    @Transactional
    public Boolean deleteInvenBuilding(UUID userUUID, DeleteBuildingRequest request){
        Optional<UserBuilding> byUserAndBuildingId = buildingINVTRepository.findByUserAndBuildingId(userUUID, request.getItemId());

        if (byUserAndBuildingId.isPresent()){
            if (byUserAndBuildingId.get().getOwnedQuantity() < 2){ // 만약 1개이하라면 삭제
                UserBuilding userBuilding = byUserAndBuildingId.get();
                buildingINVTRepository.delete(userBuilding);
            }else {
                UserBuilding userBuilding = byUserAndBuildingId.get(); // 2개이상이면 수량 -1
                userBuilding.setOwnedQuantity(userBuilding.getOwnedQuantity()-1);
            }
        }else{
            System.out.println("잘못된 정보입니다.");
        }
        MarketBuildingInsertRequest marketBuildingInsertRequest = new MarketBuildingInsertRequest(
                userUUID,
                request.getItemId(),
                request.getName(),
                request.getGrade(),
                request.getBuildingType(),
                request.getPrice(),
                request.getImagePath());
        marketInsertBuildingProducer.send(marketBuildingInsertRequest);
        return true;
    }

    @Transactional
    public void updateUpgrade(UpgradeRequest result){
        UserAnimal qtyFindByInven = animalINVTRepository.findByInven(result.userUUID(), result.itemId());
        if (qtyFindByInven.getOwnedQuantity() == 0){ //강화된 동물이 있으면 +1 없으면 새로추가
            INVTRequest invtRequest = new INVTRequest(result.userUUID(), result.itemId(), 1, 0, result.buff()); // 강화된 동물을 저장한다.
            animalINVTRepository.save(invtRequest.toEntity());
        }else {
            qtyFindByInven.setOwnedQuantity(qtyFindByInven.getOwnedQuantity() + 1);
        }
    }
}
