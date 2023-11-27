package com.example.aniamlwaruser.service;

import com.example.aniamlwaruser.domain.dto.MixRequest;
import com.example.aniamlwaruser.domain.entity.*;
import com.example.aniamlwaruser.domain.kafka.MarketInsertAnimalProducer;
import com.example.aniamlwaruser.domain.kafka.MarketInsertBuildingProducer;
import com.example.aniamlwaruser.domain.kafka.MatchProducer;
import com.example.aniamlwaruser.domain.request.*;
import com.example.aniamlwaruser.domain.response.AnimalsResponse;
import com.example.aniamlwaruser.domain.response.BuildingsResponse;
import com.example.aniamlwaruser.domain.response.UserResponse;
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
    private final MatchProducer matchProducer;
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

    public AnimalsResponse getAnimal(UUID userUUID, Long itemId){
        UserAnimal byUserUUID = animalINVTRepository.findByInven(userUUID, itemId);
        return new AnimalsResponse(
                byUserUUID.getId(),
                byUserUUID.getAnimal(),
                byUserUUID.getOwnedQuantity(),
                byUserUUID.getPlacedQuantity(),
                byUserUUID.getUpgrade());
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
        UserAnimal qtyFindByInven = animalINVTRepository.findByInvenAndBuff(result.userUUID(), result.itemId(), result.buff());
        if (qtyFindByInven == null){ //강화된 동물이 있으면 +1 없으면 새로추가
            INVTRequest invtRequest = new INVTRequest(result.userUUID(), result.itemId(), 1, 0, result.buff()); // 강화된 동물을 저장한다.
            animalINVTRepository.save(invtRequest.toEntity());
        }else {
            qtyFindByInven.setOwnedQuantity(qtyFindByInven.getOwnedQuantity() + 1);
            animalINVTRepository.save(qtyFindByInven);
        }
    }


    @Transactional
    public Boolean updatePlacedQuantity(UUID userUUID, UpdateItem updateItem) {
        EntityType entityType = updateItem.getEntityType();
        Long itemId = updateItem.getItemId();
        int newPlacedQuantity = updateItem.getPlacedQuantity();

        if (entityType == EntityType.ANIMAL) {
            Optional<UserAnimal> userAnimalOpt = animalINVTRepository.findByUserAndAnimalId(userUUID, itemId);
            if (userAnimalOpt.isPresent()) {
                UserAnimal userAnimal = userAnimalOpt.get();
                if(newPlacedQuantity <= userAnimal.getOwnedQuantity()) {
                    userAnimal.setPlacedQuantity(newPlacedQuantity);
                }
            }
        } else if (entityType == EntityType.BUILDING) {
            Optional<UserBuilding> userBuildingOpt = buildingINVTRepository.findByUserAndBuildingId(userUUID, itemId);
            if (userBuildingOpt.isPresent()) {
                UserBuilding userBuilding = userBuildingOpt.get();
                if(newPlacedQuantity <= userBuilding.getOwnedQuantity()) {
                    userBuilding.setPlacedQuantity(newPlacedQuantity);
                }
            }
        }

        User user = userRepository.findById(userUUID).orElse(null);
        if (user != null) {
            user.calculateTotalRates();
            user.updateBattleStats();

            UserResponse userResponse = UserResponse.builder()
                    .uuid(user.getUserUUID())
                    .attackPower(user.getAttackPower())
                    .defensePower(user.getDefensePower())
                    .life(user.getLife())
                    .build();

            matchProducer.send(userResponse);
        }
        return true;
    }


    @Transactional
    public boolean removePlace(UUID userUUID, RemoveItem removeItem) {
        EntityType entityType = removeItem.getEntityType();
        Long itemId = removeItem.getItemId();
        int removeAmount = removeItem.getRemoveQuantity(); // 제거할 양

        if (entityType == EntityType.ANIMAL) {
            Optional<UserAnimal> userAnimalOpt = animalINVTRepository.findByUserAndAnimalId(userUUID, itemId);
            if (userAnimalOpt.isPresent()) {
                UserAnimal userAnimal = userAnimalOpt.get();
                if (userAnimal.getPlacedQuantity() >= removeAmount) {
                    userAnimal.setPlacedQuantity(userAnimal.getPlacedQuantity() - removeAmount);
                }
            }
        } else if (entityType == EntityType.BUILDING) {
            Optional<UserBuilding> userBuildingOpt = buildingINVTRepository.findByUserAndBuildingId(userUUID, itemId);
            if (userBuildingOpt.isPresent()) {
                UserBuilding userBuilding = userBuildingOpt.get();
                if (userBuilding.getPlacedQuantity() >= removeAmount) {
                    userBuilding.setPlacedQuantity(userBuilding.getPlacedQuantity() - removeAmount);
                }
            }
        }

        User user = userRepository.findById(userUUID).orElse(null);
        if (user != null) {
            user.calculateTotalRates();
            user.updateBattleStats();

            UserResponse userResponse = UserResponse.builder()
                    .uuid(user.getUserUUID())
                    .attackPower(user.getAttackPower())
                    .defensePower(user.getDefensePower())
                    .life(user.getLife())
                    .build();

            matchProducer.send(userResponse);
        }
        return true;
    }


    public void saveInventoryAndDeleteMixed(MixRequest mixRequest) {
        UUID userUUID = mixRequest.getUserUUID();
        User user = userRepository.findById(userUUID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User UUID"));
        Long resultId = mixRequest.getMixResultId();
        EntityType entityType = mixRequest.getEntityType();

        switch (entityType) {
            case ANIMAL:
                Animal animal = animalRepository.findById(resultId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Animal Id"));

                Optional<UserAnimal> existingUserAnimal = animalINVTRepository.findByUserAndAnimalId(userUUID, animal.getAnimalId());
                if (existingUserAnimal.isPresent()) {
                    // 이미 존재하는 경우, 소유 수량만 증가
                    UserAnimal userAnimal = existingUserAnimal.get();
                    userAnimal.setOwnedQuantity(userAnimal.getOwnedQuantity() + 1);
                    animalINVTRepository.save(userAnimal);
                } else {
                    // 새로운 항목 추가
                    UserAnimal newUserAnimal = UserAnimal.builder()
                            .user(user)
                            .animal(animal)
                            .ownedQuantity(1)
                            .placedQuantity(0)
                            .upgrade(0)
                            .build();
                    animalINVTRepository.save(newUserAnimal);
                }
                break;
            case BUILDING:
                Building building = buildingRepository.findById(resultId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Building Id"));

                Optional<UserBuilding> existingUserBuilding = buildingINVTRepository.findByUserAndBuildingId(userUUID, building.getBuildingId());
                if (existingUserBuilding.isPresent()) {
                    // 이미 존재하는 경우, 소유 수량만 증가
                    UserBuilding userBuilding = existingUserBuilding.get();
                    userBuilding.setOwnedQuantity(userBuilding.getOwnedQuantity() + 1);
                    buildingINVTRepository.save(userBuilding);
                } else {
                    // 새로운 항목 추가
                    UserBuilding newUserBuilding = UserBuilding.builder()
                            .user(user)
                            .building(building)
                            .ownedQuantity(1)
                            .placedQuantity(0)
                            .build();
                    buildingINVTRepository.save(newUserBuilding);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid Entity Type");
        }
        // 합성에 사용된 아이템들을 인벤토리에서 삭제하는 로직
        List<Long> selectedItems = mixRequest.getSelectedList();


        switch (entityType) {
            case ANIMAL:
                // 동물 합성에 사용된 아이템 삭제 또는 수량 감소
                for (Long animalId : selectedItems) {
                    animalINVTRepository.findByUserAndAnimalId(userUUID, animalId).ifPresent(userAnimal -> {
                        int currentQuantity = userAnimal.getOwnedQuantity();
                        if (currentQuantity > 1) {
                            userAnimal.setOwnedQuantity(currentQuantity - 1);
                            animalINVTRepository.save(userAnimal);
                        } else {
                            animalINVTRepository.delete(userAnimal);
                        }
                    });
                }
                break;
            case BUILDING:
                // 건물 합성에 사용된 아이템 삭제 또는 수량 감소
                for (Long buildingId : selectedItems) {
                    buildingINVTRepository.findByUserAndBuildingId(userUUID, buildingId).ifPresent(userBuilding -> {
                        int currentQuantity = userBuilding.getOwnedQuantity();
                        if (currentQuantity > 1) {
                            userBuilding.setOwnedQuantity(currentQuantity - 1);
                            buildingINVTRepository.save(userBuilding);
                        } else {
                            buildingINVTRepository.delete(userBuilding);
                        }
                    });
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid Entity Type");
        }
    }
}


