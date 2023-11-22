package com.example.aniamlwaruser.service;


import com.example.aniamlwaruser.domain.dto.DrawResponse;
import com.example.aniamlwaruser.domain.dto.MixRequest;
import com.example.aniamlwaruser.domain.dto.TerrainRequestDto;
import com.example.aniamlwaruser.domain.dto.TerrainResponseDto;
import com.example.aniamlwaruser.domain.entity.*;
import com.example.aniamlwaruser.domain.request.DrawRequest;
import com.example.aniamlwaruser.domain.request.UserUpdateRequest;
import com.example.aniamlwaruser.domain.response.ReTerrainResponse;
import com.example.aniamlwaruser.domain.response.UserResponse;
import com.example.aniamlwaruser.repository.*;
import com.example.aniamlwaruser.domain.kafka.UpdateTerrainProducer;
import jakarta.transaction.Transactional;
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


    @Transactional
    public void saveInventoryAndDeleteMixed(MixRequest mixRequest) {
        if(mixRequest.getEntityType() == EntityType.ANIMAL) { // 동물 합성일 때
            System.out.println("mixRequestSSSSSSSAAAAAAAAAAVVVVVVVEEEEEEEEEE  ANIMAL  "+mixRequest);
            saveAnimalInventory(mixRequest); // 합성 결과 저장 실행
            deleteMixedAnimal(mixRequest.getSelectedList()); // 인벤토리 삭제 실행
        } else { // 건물 합성일 떄
            System.out.println("mixRequestSSSSSSSAAAAAAAAAAVVVVVVVEEEEEEEEEE BUILDING "+mixRequest);
            saveBuildingInventory(mixRequest);
            deleteMixedBuilding(mixRequest.getSelectedList());
        }
    }

    public void saveAnimalInventory(MixRequest mixRequest) {
            User user = userRepository.findByUserUUID(mixRequest.getUserUUID())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Animal animal = animalRepository.findById(mixRequest.getMixResultId())
                    .orElseThrow(() -> new RuntimeException("Animal not found"));

        // Check if the animal already exists in the user's inventory
            UserAnimal userAnimal = userAnimalRepository.findByUserAndAnimal(user, animal)
                    .orElseGet(() -> UserAnimal.builder() // 없던 동물이라면
                            .user(user)
                            .animal(animal)
                            .ownedQuantity(0)
                            .placedQuantity(0)
                            .upgrade(0)
                            .build());

            // Update owned quantity
            userAnimal.setOwnedQuantity(userAnimal.getOwnedQuantity() + 1); // 원래 가지고 있던 동물이라면 getOwnedQuantity +1

            // Save the updated/ new user animal
            userAnimalRepository.save(userAnimal);
    }

    public void saveBuildingInventory(MixRequest mixRequest) {
        // 건물 합성 결과 넣기
            User user = userRepository.findByUserUUID(mixRequest.getUserUUID())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Building building = buildingRepository.findById(mixRequest.getMixResultId())
                    .orElseThrow(() -> new RuntimeException("Animal not found"));

            // Check if the animal already exists in the user's inventory
            UserBuilding userBuilding = userBuildingRepository.findByUserAndBuilding(user, building)
                    .orElseGet(() -> UserBuilding.builder()
                            .user(user)
                            .building(building)
                            .ownedQuantity(1) // If not present, start with zero
                            .placedQuantity(0) // Assume new animal is not placed  TODO 이미 가지고 있는 동물이라면?
                            .build());

            // Update owned quantity
            userBuilding.setOwnedQuantity(userBuilding.getOwnedQuantity() + 1);

            // Save the updated/ new user animal
            userBuildingRepository.save(userBuilding);
    }

    @Transactional
    public void deleteMixedAnimal(List<Long> selectedUserAnimalIds) {
        if (selectedUserAnimalIds != null && !selectedUserAnimalIds.isEmpty()) {
            for (long animalId: selectedUserAnimalIds) {
                UserAnimal userAnimal = userAnimalRepository.findByAnimal_AnimalId(animalId).orElseThrow(() -> new RuntimeException("NOT FOUND ANIMAL ID"));
                if (userAnimal.getOwnedQuantity() > 1) {
                    userAnimal.setOwnedQuantity(userAnimal.getOwnedQuantity() -1);
                } else {
                    userAnimalRepository.deleteById(userAnimal.getId()); // count가 1개인걸 선택하면 무조건 삭제이기 떄문에 위에서 for문 돌려준거 바로 삭제
                }
            }
        }
    }

    public void deleteMixedBuilding(List<Long> selectedUserBuildingIds) {
        if (selectedUserBuildingIds != null && !selectedUserBuildingIds.isEmpty()) {
            for (long buildingId : selectedUserBuildingIds) {
                UserBuilding userBuilding = userBuildingRepository.findByBuilding_BuildingId(buildingId).orElseThrow(() -> new RuntimeException("NOT FOUND BUILDING ID"));
                if(userBuilding.getOwnedQuantity() > 1) {
                    userBuilding.setOwnedQuantity(userBuilding.getOwnedQuantity() -1);
                } else {
                    userBuildingRepository.deleteById(userBuilding.getId());
                }
            }
        }
    }
}