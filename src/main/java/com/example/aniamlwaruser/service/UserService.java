package com.example.aniamlwaruser.service;


import com.example.aniamlwaruser.domain.dto.SendDrawResponse;
import com.example.aniamlwaruser.domain.dto.MixRequest;
import com.example.aniamlwaruser.domain.dto.TerrainResponseDto;
import com.example.aniamlwaruser.domain.entity.Animal;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.entity.UserAnimal;
import com.example.aniamlwaruser.domain.kafka.GenerateTerrainProducer;
import com.example.aniamlwaruser.domain.request.DrawRequest;
import com.example.aniamlwaruser.domain.request.UserUpdateRequest;
import com.example.aniamlwaruser.domain.response.UserResponse;
import com.example.aniamlwaruser.repository.AnimalINVTRepository;
import com.example.aniamlwaruser.repository.AnimalRepository;
import com.example.aniamlwaruser.domain.kafka.UpdateTerrainProducer;
import com.example.aniamlwaruser.repository.UserAnimalRepository;
import com.example.aniamlwaruser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final GenerateTerrainProducer generateTerrainProducer;
    private final AnimalINVTRepository animalINVTRepository;
    private final UpdateTerrainProducer updateTerrainProducer;
    private final UserAnimalRepository userAnimalRepository;


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

    public void updateUserLandForm(TerrainResponseDto terrainResponseDto) {
        User user = userRepository.findByUserUUID(terrainResponseDto.getUserUUID())
                .orElseThrow(() -> new IllegalArgumentException("User not found UUID: " + terrainResponseDto.getUserUUID()));

        user.updateLandForm(terrainResponseDto.getLandForm());
        userRepository.save(user);
    }

    public void requestTerrain(UUID userUUID) {
        int requiredGold = 5000;
        User user = userRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> new RuntimeException("user Not found"));

        if (user.getFreeTerrainNum() > 0) {
            user.minusFreeTerrainNum();
        } else {
            if (user.getGold() < requiredGold) {
                throw new RuntimeException("Not enough gold");
            }
            user.minusGold(requiredGold);
        }
        updateTerrainProducer.updateTerrain(userUUID);
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

    public void insertDrawResponse(List<SendDrawResponse> result) {
        Map<String, Long> animalCountMap = result.stream()
                .collect(Collectors.groupingBy(SendDrawResponse::getName, Collectors.counting()));

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
        // saveInventory 실행
        saveInventory(mixRequest);
        // deleteMixed 실행
        deleteMixed(mixRequest.getUserAnimalList());
    }

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

    public void deleteMixed(List<Long> selectedUserAnimalIds) {
        if (selectedUserAnimalIds != null && !selectedUserAnimalIds.isEmpty()) {
            // Batch delete by IDs to improve performance
            userAnimalRepository.deleteAllByUserAnimalIdIn(selectedUserAnimalIds);
        }
    }
}