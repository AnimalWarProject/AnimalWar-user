package com.example.aniamlwaruser.service;


import com.example.aniamlwaruser.domain.dto.TerrainResponseDto;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.domain.kafka.DrawConsumer;
import com.example.aniamlwaruser.domain.kafka.DrawProducer;
import com.example.aniamlwaruser.domain.kafka.GenerateTerrainProducer;
import com.example.aniamlwaruser.domain.request.DrawRequest;
import com.example.aniamlwaruser.domain.response.DrawResultResponseDto;
import com.example.aniamlwaruser.domain.response.UserResponse;
import com.example.aniamlwaruser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GenerateTerrainProducer generateTerrainProducer;
    private final DrawProducer drawProducer;
    private final DrawConsumer drawConsumer;


    // 아이디로 회원 정보 조회
    public UserResponse findUserByUserId(String id) {
        User user = userRepository.findByid(id)
                .orElseThrow(() -> new IllegalArgumentException("USER NOT FOUND FOR USERID: " + id));

        return UserResponse.builder()
                .id(user.getId())
                .nickName(user.getNickName())
                .food(user.getFood())
                .iron(user.getIron())
                .wood(user.getWood())
                .gold(user.getGold())
                .attackPower(user.getAttackPower())
                .defensePower(user.getDefensePower())
                .battlePoint(user.getBattlePoint())
                .profileImage(user.getProfileImage())
                .species(user.getSpecies())
                .build();
    }


    public UserResponse findUserByUserUUId(UUID userUUID) {
        User user = userRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> new IllegalArgumentException("USER NOT FOUND FOR USERID: " + userUUID));

        return UserResponse.builder()
                .id(user.getId())
                .nickName(user.getNickName())
                .food(user.getFood())
                .iron(user.getIron())
                .wood(user.getWood())
                .gold(user.getGold())
                .attackPower(user.getAttackPower())
                .defensePower(user.getDefensePower())
                .battlePoint(user.getBattlePoint())
                .profileImage(user.getProfileImage())
                .species(user.getSpecies())
                .build();
    }

    public void updateUserLandForm(TerrainResponseDto terrainResponseDto) {
        Optional<User> optionalUser = userRepository.findByUserUUID(terrainResponseDto.getUserUUID());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setLandForm(terrainResponseDto.getLandForm());

            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found for UUID: " + terrainResponseDto.getUserUUID());
        }
    }


    public void requestTerrain(UUID userUUID) {
        int requiredGold = 5000;

        Optional<User> optionalUser = userRepository.findByUserUUID(userUUID);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getFreeTerrainNum() > 0) {
                user.setFreeTerrainNum(user.getFreeTerrainNum() - 1);
            } else {
                if (user.getGold() < requiredGold) {
                    throw new RuntimeException("Not enough gold");
                }
                user.setGold(user.getGold() - requiredGold);
            }
            generateTerrainProducer.requestTerrain(userUUID);
        } else {
            throw new RuntimeException("No user");
        }
    }

    public List<DrawResultResponseDto> requestDraw(DrawRequest request) {
        Optional<User> optionalUser = userRepository.findByUserUUID(request.userUUID());
        System.out.println("service" + request.count());
        System.out.println("service" + request.userUUID());
        List<DrawResultResponseDto> list;
        if (optionalUser.isPresent()) { // 유저의 정보가 존재한다면
            drawProducer.requestDraw(request.count()); // kafka를 통해서 draw서비스를 사용
            list = drawConsumer.getReceivedData();
            return list;
        } else {
            throw new RuntimeException("No user");
        }
    }



    // 매일 자정 무료 맵 횟수 3회로 주기
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetFreeTerrainNum() {
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            user.setFreeTerrainNum(3);
        }
        userRepository.saveAll(allUsers);
    }
}
