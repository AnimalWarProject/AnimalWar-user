package com.example.aniamlwaruser.service;


import com.example.aniamlwaruser.domain.dto.TerrainResponseDto;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.kafka.UpdateTerrainProducer;
import com.example.aniamlwaruser.domain.request.UserUpdateRequest;
import com.example.aniamlwaruser.domain.response.UserResponse;
import com.example.aniamlwaruser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UpdateTerrainProducer updateTerrainProducer;


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
        User user = userRepository.findByNickname(nickName)
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
            if(user.getGold() < requiredGold) {
                throw new RuntimeException("Not enough gold");
            }
            user.minusGold(requiredGold);
        }
        updateTerrainProducer.updateTerrain(userUUID);
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

}
