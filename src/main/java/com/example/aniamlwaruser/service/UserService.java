package com.example.aniamlwaruser.service;


import com.example.aniamlwaruser.domain.dto.DrawResponse;
import com.example.aniamlwaruser.domain.dto.MixRequest;
import com.example.aniamlwaruser.domain.dto.TerrainRequestDto;
import com.example.aniamlwaruser.domain.dto.TerrainResponseDto;
import com.example.aniamlwaruser.domain.entity.*;
import com.example.aniamlwaruser.domain.kafka.BuyItemProducer;
import com.example.aniamlwaruser.domain.kafka.CancelItemProducer;
import com.example.aniamlwaruser.domain.request.*;
import com.example.aniamlwaruser.domain.response.ReTerrainResponse;
import com.example.aniamlwaruser.domain.response.UserResponse;
import com.example.aniamlwaruser.repository.*;
import com.example.aniamlwaruser.domain.kafka.UpdateTerrainProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final BuildingRepository buildingRepository;
    private final AnimalINVTRepository animalINVTRepository;
    private final BuildingINVTRepository buildingINVTRepository;
    private final UpdateTerrainProducer updateTerrainProducer;
    private final UserAnimalRepository userAnimalRepository;
    private final UserBuildingRepository userBuildingRepository;
    private final BuyItemProducer buyItemProducer;
    private final CancelItemProducer cancelItemProducer;


    // 아이디로 회원 정보 조회
    public UserResponse findUserByUserId(String id) {
        User user = userRepository.findByid(id)
                .orElseThrow(() -> new IllegalArgumentException("USER NOT FOUND USERID: " + id));

        return UserResponse.userResponseBuild(user);
    }


    public UserResponse findUserByUserUUId(UUID userUUID) {
        User user = userRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> new IllegalArgumentException("USER NOT FOUND USERID: " + userUUID));

        return UserResponse.userResponseBuild(user);
    }

    public UserResponse findUserByNickName(String nickName) {
        User user = userRepository.findByNickName(nickName)
                .orElseThrow(() -> new IllegalArgumentException("USER NOT FOUND USERID: " + nickName));

        return UserResponse.userResponseBuild(user);
    }


    //마이페이지에서 정보 수정할 때 사용
    public void updateUser(UUID userUUID, UserUpdateRequest request) {
        User existingUser = userRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> new IllegalArgumentException("USER NOT FOUND UUID: " + userUUID));

        if (request.getId() != null) {
            existingUser.updateId(request.getId());
        }
        if (request.getPassword() != null) {
            existingUser.updatePassword(request.getPassword());
        }
        if (request.getNickName() != null) {
            existingUser.updateNickName(request.getNickName());
        }
        if (request.getProfileImage() != null) {
            existingUser.updateProfileImage(request.getProfileImage());
        }

        userRepository.save(existingUser);
    }
    



    public void updateUserTerrainData(TerrainResponseDto terrainResponseDto) {
        User user = userRepository.findByUserUUID(terrainResponseDto.getUserUUID())
                .orElseThrow(() -> new IllegalArgumentException("User not found UUID: " + terrainResponseDto.getUserUUID()));

        user.updateLandForm(terrainResponseDto.getDominantlandForm());
        user.updateSea(terrainResponseDto.getSea());
        user.updateLand(terrainResponseDto.getLand());
        user.updateMountain(terrainResponseDto.getMountain());
        userRepository.save(user);
    }

    public ReTerrainResponse requestTerrain(UUID userUUID) {
        int requiredGold = 5000;
        User user = userRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> new RuntimeException("user Not found"));

        // 무료 지형이 남아있는지 또는 충분한 골드가 있는지 확인합니다.
        if (user.getFreeTerrainNum() > 0) {
            user.minusFreeTerrainNum(); // 무료 지형 번호를 하나 줄입니다.
        } else {
            if (user.getGold() < requiredGold) {
                throw new RuntimeException("Not enough gold"); // 예외를 던집니다.
            }
            user.minusGold(requiredGold); // 필요한 골드를 차감합니다.
        }
        userRepository.save(user); // 변경 사항을 저장합니다.

        // 맵 생성 요청을 Kafka 토픽으로 전송합니다.
        TerrainRequestDto terrainRequestDto = new TerrainRequestDto(user.getUserUUID());
        updateTerrainProducer.updateTerrain(terrainRequestDto);
        return new ReTerrainResponse(user.getGold(), user.getSea(), user.getLand(), user.getMountain());
    }

    public void requestDraw(DrawRequest request){
        User byUserUUID = userRepository.findByUserUUID(request.userUUID()).orElseThrow(()-> new RuntimeException("Not Found User"));
        int requiredGold = request.count() * 1000;
        if (byUserUUID.getGold() >= requiredGold){
            byUserUUID.minusGold(requiredGold);
        }else {
            throw new RuntimeException("Not enough gold");
        }
    }

    public void insertAnimalDrawResponse(List<DrawResponse> result) { // 동물 뽑기 결과 저장
        System.out.println(result.get(0));
        Map<String, Long> animalCountMap = result.stream()
                .collect(Collectors.groupingBy(DrawResponse::getName, Collectors.counting()));

        User byUserUUID = userRepository.findByUserUUID(result.get(0).getUserUUID())
                .orElseThrow(() -> new RuntimeException("Not Found User"));

        for (Map.Entry<String, Long> entry : animalCountMap.entrySet()) {
            String animalName = entry.getKey();
            Long count = entry.getValue();

            Animal animal = animalRepository.findByName(animalName)
                    .orElseThrow(() -> new IllegalArgumentException("Animal not found with name: " + animalName));

            UserAnimal userAnimal = animalINVTRepository.findByUserAndAnimal(byUserUUID, animal)
                    .orElseGet(() -> {
                        UserAnimal newUserAnimal = new UserAnimal();
                        newUserAnimal.setUser(byUserUUID);
                        newUserAnimal.setAnimal(animal);
                        newUserAnimal.setOwnedQuantity(0);
                        newUserAnimal.setPlacedQuantity(0);
                        newUserAnimal.setUpgrade(0);
                        return newUserAnimal;
                    });

            userAnimal.setOwnedQuantity(userAnimal.getOwnedQuantity() + count.intValue());
            animalINVTRepository.save(userAnimal);
        }
    }

    public void insertBuildingDrawResponse(List<DrawResponse> result) { // 건물 뽑기 결과 저장
        Map<String, Long> buildingCountMap = result.stream()
                .collect(Collectors.groupingBy(DrawResponse::getName, Collectors.counting()));

        User byUserUUID = userRepository.findByUserUUID(result.get(0).getUserUUID())
                .orElseThrow(() -> new RuntimeException("Not Found User"));

        for (Map.Entry<String, Long> entry : buildingCountMap.entrySet()) {
            String buildingName = entry.getKey();
            Long count = entry.getValue();

            Building building = buildingRepository.findByName(buildingName)
                    .orElseThrow(() -> new IllegalArgumentException("Animal not found with name: " + buildingName));

            UserBuilding userBuilding = buildingINVTRepository.findByUserAndBuilding(byUserUUID, building)
                    .orElseGet(() -> {
                        UserBuilding newUserBuilding = new UserBuilding();
                        newUserBuilding.setUser(byUserUUID);
                        newUserBuilding.setBuilding(building);
                        newUserBuilding.setOwnedQuantity(0);
                        newUserBuilding.setPlacedQuantity(0);
                        return newUserBuilding;
                    });

            userBuilding.setOwnedQuantity(userBuilding.getOwnedQuantity() + count.intValue());
            buildingINVTRepository.save(userBuilding);
        }
    }

    // 매일 자정 무료 맵 횟수 3회로 주기
    @Scheduled(cron = "0 0 0 * * ?")
    @Async
    public void resetFreeTerrainNum() {
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            user.resetFreeTerrainNum();
        }
        userRepository.saveAll(allUsers);
    }


    public void buyAnimal(UUID userUUID, BuyItemRequest request) {
        int price = request.getPrice();
        int ownedQuantity = 1; // 새로운 동물의 기본 수량
        int placedQuantity = 0;
        User user = userRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 동물인지 건물인지 확인.
        Optional<Animal> animalByName = animalRepository.findByName(request.getName());
        Optional<Building> buildingByName = buildingRepository.findByName(request.getName());

        if (animalByName.isPresent()){
            Optional<UserAnimal> existingAnimal = // 해당 동물이 인벤토리에 중복되는지 검사.
                    animalINVTRepository.findByUserUUIDAndAnimalNameAndBuff(userUUID, request.getName(), request.getBuff());

            if (existingAnimal.isPresent()) {
                UserAnimal animal = existingAnimal.get();
                ownedQuantity = animal.getOwnedQuantity() + 1; // 있으면 수량만 +1
                placedQuantity = animal.getPlacedQuantity();
            }
            // 사용자의 골드에서 돈 차감
            user.setGold(user.getGold() - price);

            // 새로운 동물 정보 생성 및 저장
            InsertAnimalRequest insertAnimalRequest = new InsertAnimalRequest(
                    userUUID,
                    request.getItemId(),
                    ownedQuantity,
                    placedQuantity,
                    request.getBuff()
            );

            System.out.println("동물 Buy 하는중");
            
            animalINVTRepository.save(insertAnimalRequest.toEntity());
            // kafka 를 통해서 해당 아이템 거래 Btn비활성화 만들어야함.
            BuyBtnRequest buyBtnRequest = new BuyBtnRequest(request.getUserId(), request.getItemId());
            buyItemProducer.send(buyBtnRequest);

        }else if (buildingByName.isPresent()){
            Optional<UserBuilding> byUserUUIDAndBuildingNameAndType =
                    buildingINVTRepository.findByUserUUIDAndBuildingNameAndType(userUUID, request.getName(), request.getType());

            if (byUserUUIDAndBuildingNameAndType.isPresent()) {
                UserBuilding building = byUserUUIDAndBuildingNameAndType.get();
                ownedQuantity = building.getOwnedQuantity() + 1; // 있으면 수량만 +1
                placedQuantity = building.getPlacedQuantity();
            }
            // 사용자의 골드에서 돈 차감
            user.setGold(user.getGold() - price);

            // 새로운 동물 정보 생성 및 저장
            InsertBuildingRequest insertBuildingRequest = new InsertBuildingRequest(
                    userUUID,
                    request.getItemId(),
                    ownedQuantity,
                    placedQuantity
            );

            System.out.println("건물 Buy 하는중");
            
            buildingINVTRepository.save(insertBuildingRequest.toEntity());
            BuyBtnRequest buyBtnRequest = new BuyBtnRequest(request.getUserId(), request.getItemId());
            buyItemProducer.send(buyBtnRequest);
        }
    }

    public void cancelItem(UUID userUUID, CancelItemRequest request){
        // 동물인지 건물인지 확인.
        Optional<Animal> animalByName = animalRepository.findByName(request.getName());
        Optional<Building> buildingByName = buildingRepository.findByName(request.getName());

        if (animalByName.isPresent()){
            Optional<UserAnimal> byUserUUIDAndAnimalNameAndBuff =
                    animalINVTRepository.findByUserUUIDAndAnimalNameAndBuff(userUUID, request.getName(), request.getBuff());

            if (byUserUUIDAndAnimalNameAndBuff.isPresent()){
                UserAnimal userAnimal = byUserUUIDAndAnimalNameAndBuff.get();
                InsertAnimalRequest animalRequest = new InsertAnimalRequest(
                        userUUID,
                        request.getItemId(),
                        userAnimal.getOwnedQuantity()+1,
                        userAnimal.getPlacedQuantity(),
                        request.getBuff()
                );
                animalINVTRepository.save(animalRequest.toEntity());
            }else {
                InsertAnimalRequest animalRequest = new InsertAnimalRequest(
                        userUUID,
                        request.getItemId(),
                        1,
                        0,
                        request.getBuff()
                );
                animalINVTRepository.save(animalRequest.toEntity());
            }
        }else if (buildingByName.isPresent()){
            Optional<UserBuilding> byUserUUIDAndBuildingNameAndType =
                    buildingINVTRepository.findByUserUUIDAndBuildingNameAndType(userUUID, request.getName(), request.getType());

            if (byUserUUIDAndBuildingNameAndType.isPresent()){
                UserBuilding userBuilding = byUserUUIDAndBuildingNameAndType.get();
                InsertBuildingRequest buildingRequest = new InsertBuildingRequest(
                        userUUID,
                        request.getItemId(),
                        userBuilding.getOwnedQuantity()+1,
                        userBuilding.getPlacedQuantity()
                );
                buildingINVTRepository.save(buildingRequest.toEntity());
            }else {
                InsertBuildingRequest buildingRequest = new InsertBuildingRequest(
                        userUUID,
                        request.getItemId(),
                        1,
                       0
                );
                buildingINVTRepository.save(buildingRequest.toEntity());
            }
        }

        CancelBtnRequest cancelBtnRequest = new CancelBtnRequest(userUUID, request.getItemId());
        cancelItemProducer.send(cancelBtnRequest);
    }



//    @Transactional
//    public void saveInventoryAndDeleteMixed(MixRequest mixRequest) {
//        // saveInventory 실행
//        saveInventory(mixRequest);
//        // deleteMixed 실행
//        deleteMixed(mixRequest.getUserAnimalList());
//    }

    public void saveInventory(MixRequest mixRequest) {
        User user = userRepository.findByUserUUID(mixRequest.getUserUUID())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Animal animal = animalRepository.findById(mixRequest.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal not found"));

        // Check if the animal already exists in the user's inventory
        UserAnimal userAnimal = userAnimalRepository.findByUserAndAnimal(user, animal)
                .orElseGet(() -> UserAnimal.builder()
                        .user(user)
                        .animal(animal)
                        .ownedQuantity(0) // If not present, start with zero
                        .placedQuantity(0) // Assume new animal is not placed
                        .upgrade(0) // Assume upgrades start at 0 for new animal
                        .build());

        // Update owned quantity
        userAnimal.setOwnedQuantity(userAnimal.getOwnedQuantity() + 1);

        // Save the updated/ new user animal
        userAnimalRepository.save(userAnimal);
    }

//    public void deleteMixed(List<Long> selectedUserAnimalIds) {
//        if (selectedUserAnimalIds != null && !selectedUserAnimalIds.isEmpty()) {
//            // Batch delete by IDs to improve performance
//            userAnimalRepository.deleteAllByUserAnimalIdIn(selectedUserAnimalIds);
//        }
//    }
}